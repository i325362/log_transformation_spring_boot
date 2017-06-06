package com.sap.xm.scheduler.batch;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.sap.xm.scheduler.batch.processor.CsvProcessor;
import com.sap.xm.scheduler.batch.processor.PersonItemProcessor;
import com.sap.xm.scheduler.batch.processor.XmlProcessor;
import com.sap.xm.scheduler.batch.reader.BatchReaderQueryPool;
import com.sap.xm.scheduler.batch.reader.Report;
import com.sap.xm.scheduler.models.Company;
import com.sap.xm.scheduler.models.RawLog;
import com.sap.xm.scheduler.models.Record;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration extends DefaultBatchConfigurer {

	@Autowired
	JobBuilderFactory jobBuilderFactory;
	@Autowired
	@Qualifier("secondaryDataSource")
	DataSource secondaryDataSource;
	@Autowired
	@Qualifier("primaryDataSource")
	DataSource primaryDataSource;
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	@Autowired
	@Qualifier("entityManagerFactory") 
	EntityManagerFactory emf;
	@Autowired
	JobCompletionNotificationListener jobCompletionNotificationListener;
	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	PersonItemProcessor personItemProcessor;

	@Autowired
	XmlProcessor xmlProcessor;
	
	@Override
	@Autowired
	public void setDataSource(@Qualifier("batchDataSource") DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	public static final String QUERY = "insert into RECORD values(?,?,?,?,?,?,?,?,?,?,?)";
	
	@Bean
	public ItemReader<CampaignTimeImpressionDTO> reader(){
		JdbcCursorItemReader<CampaignTimeImpressionDTO> reader = new JdbcCursorItemReader<>();
		reader.setDataSource(primaryDataSource);
		reader.setPreparedStatementSetter(new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement arg0) throws SQLException {
				arg0.setTimestamp(2, new Timestamp(1462359600000L));
				arg0.setTimestamp(1, new Timestamp(1462359600000L));
				arg0.setTimestamp(3, new Timestamp(1462359600000L));
				arg0.setTimestamp(4, new Timestamp(getCurrentHour().getTimeInMillis()));
			}
		});
		reader.setSql(BatchReaderQueryPool.CAMPAIGN_TIME_IMPRESSION_QUERY.getQuery());
		reader.setRowMapper(new BeanPropertyRowMapper<>(CampaignTimeImpressionDTO.class));
//		reader.setVerifyCursorPosition(false);
		reader.setFetchSize(50);
		return reader;
	}
	
	@Bean
	public ItemReader<CsvDto> read(){
		FlatFileItemReader<CsvDto> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("SAPXM_VILR.csv"));
		reader.setLineMapper(new DefaultLineMapper<CsvDto>(){{
			setLineTokenizer(new DelimitedLineTokenizer(){{
				setNames(new String[] {"OriginalProjectID", "AdCampaign", "AdPlacement", "AdZone", "AdCreative", "AdFormatCode", 
						"SiteId", "PageUrl", "RequestTimestamp", "BruttoVerweilDauer", "NettoVerweilDauer", "AdVisTimeAreaAtLeast1", 
						"AdVisTimeAreaAtLeast50", "AdVisTimeAreaAtLeast75", "AdVisTimeAreaAtLeast100", "AdMRCViewableImpressionTime", 
						"FensterHoehe", "FensterBreite", "ScreenHeight", "ScreenWidth", "ImageLeft", "ImageTop", "AdDimension", 
						"MinPosition", "MaxPosition", "InitialPosition", "CountryCode", "OS", "Browser", "DeviceType", 
						"IframeFlag", "AdStateFlag2", "AdMRCFullLengthViewableVideoFlag", "StrangenessFlag", "StrangenessCategory", 
						"AdParameterA", "AdParameterB", "AdParameterC", "AdParameterD", "AdParameterE", "AdAdvertiser"});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<CsvDto>(){{
					setTargetType(CsvDto.class);
				}});
			}});
		}});
		return reader;
	}

	@Bean
	public ItemWriter<Record> writer() throws SQLException {
		System.out.println("--------WRITER----------");
//		JdbcBatchItemWriter<Record> writer = new JdbcBatchItemWriter<>();
//		writer.setDataSource(this.secondaryDataSource);
		JpaItemWriter<Record> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(emf);
//		ItemPreparedStatementSetter<Record> preparedStatement = new ItemPreparedStatementSetter<Record>() {
//			private int i = 456;
//
//			@Override
//			public void setValues(Record arg0, PreparedStatement arg1) throws SQLException {
//				
//
//			}
//		};
//		writer.setItemPreparedStatementSetter(preparedStatement);
//		writer.setSql(QUERY);
		return writer;
	}
	
	@Bean ItemWriter<RawLog> csvWriter(){
		JpaItemWriter<RawLog> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(emf);
		return writer;
	}
	// end::readerwriterprocessor[]

	// tag::jobstep[]
	@Bean
	public ItemReader<Report> xmlReader() throws Exception{
		StaxEventItemReader<Report> reader = new StaxEventItemReader<>();
		reader.setName("ReportRead");
		reader.setResource(new ClassPathResource("sample.xml"));
		reader.setFragmentRootElementName("record");
		Jaxb2Marshaller ummarshaller = new Jaxb2Marshaller();
		ummarshaller.setClassesToBeBound(Report.class);
		ummarshaller.afterPropertiesSet();
		reader.setUnmarshaller(ummarshaller);
		return reader;
	}
	
	@Bean
	public ItemWriter<Company> xmlWriter(){
		JpaItemWriter<Company> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(emf);
		return writer;
	}
	@Bean
	public Job importUserJob() throws Exception {
		return jobBuilderFactory.get("ReadAdRequestLog").incrementer(new RunIdIncrementer()).listener(jobCompletionNotificationListener)
				.flow(this.step2())
//				.next(this.step2())
				.end().build();
	}

//	@Scheduled(initialDelay = 5000, fixedDelay=2000)
//	public void scheduleJob() throws SQLException, Exception{
//		Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
//		parameters.put("ExecutionTimestamp", new JobParameter(new Date()));
//		
//		//		importUserJob().execute(new JobExecution(System.currentTimeMillis()));;
//		jobLauncher.run(importUserJob(), new JobParameters(parameters)); 
//	}
	@Bean
	public Step step1() throws SQLException {
		return stepBuilderFactory.get("step1")
				.<CsvDto, RawLog>chunk(50)
				.reader(read())
				.processor(new CsvProcessor()) //Also add a metric for step completion notification.
				.writer(/*new CompositeItemWriter<>*/csvWriter()).build();
	}
	
	@Bean Step step2() throws SQLException{
		return stepBuilderFactory.get("step2")
				.<CampaignTimeImpressionDTO, Record>chunk(50)
				.reader(reader()).processor(personItemProcessor)
				.writer(writer()).build();
	}
	@Bean
	public Step step3() throws Exception{
		return stepBuilderFactory.get("step3")
				.<Report, Company>chunk(50)
				.reader(xmlReader()).processor(xmlProcessor)
				.writer(xmlWriter()).build();
	}
	// end::jobstep[]
	public Calendar getCurrentHour() {
		final Calendar currentHour = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		currentHour.setTime(new Date());
		currentHour.set(Calendar.MINUTE, 00);
		currentHour.set(Calendar.SECOND, 00);
		currentHour.set(Calendar.MILLISECOND, 0);
		return currentHour;
	}
	
	public Calendar getPastHour(){
		final Calendar pastHour = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		pastHour.setTime(new Date(getCurrentHour().getTimeInMillis()));
		pastHour.add(Calendar.HOUR, -1);
		return pastHour;
	}

}