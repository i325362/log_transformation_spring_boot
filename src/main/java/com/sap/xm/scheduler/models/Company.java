package com.sap.xm.scheduler.models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="COMPANY")
public class Company {

	@Id
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_GEN")
	// @GenericGenerator(name = "generator", strategy = "sequence-identity",
	// parameters = @Parameter (name = "sequence", value = "SEQ_GEN"))
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID")
	private Integer id;

	@Column(name = "REFID")
	private int refId;
	@Column(name = "NAME")
	private String name;
	@Column(name = "AGE")
	private int age;
	@Column(name = "DOB")
	private Date dob;
	@Column(name = "INCOME")
	private BigDecimal income;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getRefId() {
		return refId;
	}
	public void setRefId(int refId) {
		this.refId = refId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
}
