package com.sap.xm.scheduler.models;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="RECORD")
public class Record {

	@Id
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_GEN")
//	@GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @Parameter (name = "sequence", value = "SEQ_GEN"))
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name="ID")
	private Integer id;
	
	@Column(name="ADVERTISER_ID")
	private Long advertiserId;

	@Column(name="CAMPAIGN_ID")
	private Long campaignId;

	@Column(name="IMP_DATE")
	private Date impDate;

	@Column(name="IMP_TIME")
	private Timestamp impTime;

	@Column(name="IMPRESSIONS")
	private int impressions;

	@Column(name="CLICKS")
	private int clicks;

	@Column(name="CONVERSIONS")
	private int conversions;

	@Column(name="IMP_SPEND")
	private BigDecimal impSpend;

	@Column(name="CLK_SPEND")
	private BigDecimal clkSpend;

	@Column(name="SPEND_CURRENCY")
	private String spendCurrency;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
