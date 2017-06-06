package com.sap.xm.scheduler.batch.reader;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.sap.xm.scheduler.batch.CampaignTimeImpressionDTO;

//@Component
public class CampaignTimeImpressionReader extends JdbcCursorItemReader<CampaignTimeImpressionDTO> {

	@Autowired
	@Qualifier("primaryDataSource")
	DataSource dataSource;

	private JdbcCursorItemReader<CampaignTimeImpressionDTO> reader;

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




	@Override
	public CampaignTimeImpressionDTO read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		this.reader.setDataSource(dataSource);
		reader.setPreparedStatementSetter(new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement arg0) throws SQLException {
				arg0.setTimestamp(2, new Timestamp(getCurrentHour().getTimeInMillis()));
				arg0.setTimestamp(1, new Timestamp(getPastHour().getTimeInMillis()));
			}
		});
		reader.setSql(BatchReaderQueryPool.CAMPAIGN_TIME_IMPRESSION_QUERY.getQuery());
		this.reader.setRowMapper(new BeanPropertyRowMapper<>(CampaignTimeImpressionDTO.class));
		return this.reader.read();
	}
}
