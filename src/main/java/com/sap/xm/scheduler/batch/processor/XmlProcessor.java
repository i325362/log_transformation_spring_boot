package com.sap.xm.scheduler.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.sap.xm.scheduler.batch.reader.Report;
import com.sap.xm.scheduler.models.Company;

@Component
public class XmlProcessor implements ItemProcessor<Report, Company> {
	
	@Override
	public Company process(final Report report) throws Exception {
		
//		RawLog log = rawLogRepository.findOne(String.valueOf(report.getCampaignId()));
//		if(log != null){
			final Company company = new Company();
			company.setAge(report.getAge());
			company.setDob(report.getDob());
			company.setIncome(report.getIncome());
			company.setName(report.getName());
			company.setRefId(report.getRefId());
//		}
//		return null;
			return company;
	}
}
