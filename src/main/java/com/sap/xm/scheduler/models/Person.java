package com.sap.xm.scheduler.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.jboss.logging.Param;

@Entity
@Table(name="PERSON")
public class Person {

	@Id
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_GEN")
//	@GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @Parameter (name = "sequence", value = "SEQ_GEN"))
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name="ID")
	private Integer id;
	
	@Column(name="NAME")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
