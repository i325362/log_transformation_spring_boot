package com.sap.xm.scheduler.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.xm.scheduler.batch.CampaignTimeImpressionDTO;
import com.sap.xm.scheduler.models.RawLog;
import com.sap.xm.scheduler.models.Record;
import com.sap.xm.scheduler.repo.RawLogRepository;

@Component
public class PersonItemProcessor implements ItemProcessor<CampaignTimeImpressionDTO, Record>{
	
	@Autowired
	protected RawLogRepository rawLogRepository;
	
	@Override
	public Record process(final CampaignTimeImpressionDTO ctid) throws Exception {
		
//		RawLog log = rawLogRepository.findOne(String.valueOf(ctid.getCampaignId()));
//		if(log != null){
			final Record transformedRecord = new Record();
			transformedRecord.setAdvertiserId(ctid.getAdvertiserId());
			transformedRecord.setCampaignId(ctid.getCampaignId());
			transformedRecord.setImpDate(ctid.getImpDate());
			transformedRecord.setImpTime(ctid.getImpTime());
			transformedRecord.setImpressions(ctid.getImpressions());
			transformedRecord.setClicks(ctid.getClicks());
			transformedRecord.setConversions(ctid.getConversions());
			transformedRecord.setImpSpend(ctid.getImpSpend());
			transformedRecord.setClkSpend(ctid.getClkSpend());
			transformedRecord.setSpendCurrency(ctid.getSpendCurrency());
			return transformedRecord;
//		}
//		return null;
	}
}
