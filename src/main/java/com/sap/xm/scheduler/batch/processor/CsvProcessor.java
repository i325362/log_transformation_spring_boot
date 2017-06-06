package com.sap.xm.scheduler.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.sap.xm.scheduler.batch.CsvDto;
import com.sap.xm.scheduler.models.RawLog;

public class CsvProcessor implements ItemProcessor<CsvDto, RawLog>{

	@Override
	public RawLog process(CsvDto item) throws Exception {
		return new RawLog(item);
	}
}
