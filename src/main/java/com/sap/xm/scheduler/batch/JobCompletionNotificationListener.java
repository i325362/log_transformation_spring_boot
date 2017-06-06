package com.sap.xm.scheduler.batch;

import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {


	protected CounterService counterService;
	
	private Boolean running;
	@Autowired
	public JobCompletionNotificationListener(CounterService counterService) {
		this.counterService = counterService;
		this.running = true;
//		counterService.increment("importUserJob");
	}

	
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.FAILED){
			counterService.increment(jobExecution.getJobInstance().getJobName()+".Failed");
			this.running = false;
		}
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			counterService.increment(jobExecution.getJobInstance().getJobName());
			
//			List<Person> results = jdbcTemplate.query("SELECT first_name, last_name FROM people", new RowMapper<Person>() {
//				@Override
//				public Person mapRow(ResultSet rs, int row) throws SQLException {
//					return new Person(rs.getString(1), rs.getString(2));
//				}
//			});
//
//			for (Person person : results) {
//				log.info("Found <" + person + "> in the database.");
//			}

		}
	}
	
	public Boolean isRunning(){
		return this.running;
	}
}