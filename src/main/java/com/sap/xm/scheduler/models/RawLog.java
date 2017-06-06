package com.sap.xm.scheduler.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sap.xm.scheduler.batch.CsvDto;

@Entity
@Table(name = "RAWTABLE")
public class RawLog {

	@Column(name="ADCAMPAIGN")
	private String AdCampaign;
	@Column(name="ADCREATIVE")
	private String AdCreative;
	@Column(name="PAGEURL")
	private String PageUrl;
	@Column(name="REQUESTTIMESTAMP")
	private String RequestTimestamp;
	@Column(name="ADDIMENSION")
	private String AdDimension;
	@Column(name="COUNTRYCODE")
	private String CountryCode;
	@Id
	@Column(name="ADPARAMETERA")
	private String AdParameterA;
	
	public RawLog(){}
	
	public RawLog(CsvDto csvDto){
		this.AdCampaign = csvDto.getAdCampaign();
		this.AdCreative = csvDto.getAdCreative();
		this.PageUrl = csvDto.getPageUrl();
		this.RequestTimestamp = csvDto.getRequestTimestamp();
		this.AdDimension = csvDto.getAdDimension();
		this.CountryCode = csvDto.getCountryCode();
		this.AdParameterA = csvDto.getAdParameterA();
	}
	public String getAdCampaign() {
		return AdCampaign;
	}
	public void setAdCampaign(String adCampaign) {
		AdCampaign = adCampaign;
	}
	public String getAdCreative() {
		return AdCreative;
	}
	public void setAdCreative(String adCreative) {
		AdCreative = adCreative;
	}
	public String getPageUrl() {
		return PageUrl;
	}
	public void setPageUrl(String pageUrl) {
		PageUrl = pageUrl;
	}
	public String getRequestTimestamp() {
		return RequestTimestamp;
	}
	public void setRequestTimestamp(String requestTimestamp) {
		RequestTimestamp = requestTimestamp;
	}
	public String getAdDimension() {
		return AdDimension;
	}
	public void setAdDimension(String adDimension) {
		AdDimension = adDimension;
	}
	public String getCountryCode() {
		return CountryCode;
	}
	public void setCountryCode(String countryCode) {
		CountryCode = countryCode;
	}
	public String getAdParameterA() {
		return AdParameterA;
	}
	public void setAdParameterA(String adParameterA) {
		AdParameterA = adParameterA;
	}
	
}
