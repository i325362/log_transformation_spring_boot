package com.sap.xm.scheduler.batch;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class CampaignTimeImpressionDTO {

	private Long advertiserId;

	private Long campaignId;

	private Date impDate;

	private Timestamp impTime;

	private int impressions;

	private int clicks;

	private int conversions;

	private BigDecimal impSpend;

	private BigDecimal clkSpend;

	private String spendCurrency;

	public Long getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(Long advertiserId) {
		this.advertiserId = advertiserId;
	}

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public Date getImpDate() {
		return impDate;
	}

	public void setImpDate(Date impDate) {
		this.impDate = impDate;
	}

	public Timestamp getImpTime() {
		return impTime;
	}

	public void setImpTime(Timestamp impTime) {
		this.impTime = impTime;
	}

	public int getImpressions() {
		return impressions;
	}

	public void setImpressions(int impressions) {
		this.impressions = impressions;
	}

	public int getClicks() {
		return clicks;
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}

	public int getConversions() {
		return conversions;
	}

	public void setConversions(int conversions) {
		this.conversions = conversions;
	}

	public BigDecimal getImpSpend() {
		return impSpend;
	}

	public void setImpSpend(BigDecimal impSpend) {
		this.impSpend = impSpend;
	}

	public BigDecimal getClkSpend() {
		return clkSpend;
	}

	public void setClkSpend(BigDecimal clkSpend) {
		this.clkSpend = clkSpend;
	}

	public String getSpendCurrency() {
		return spendCurrency;
	}

	public void setSpendCurrency(String spendCurrency) {
		this.spendCurrency = spendCurrency;
	}

}
