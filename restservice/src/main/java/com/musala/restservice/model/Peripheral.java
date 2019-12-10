package com.musala.restservice.model;


import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "peripheral")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Peripheral implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String vendor;	
	
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Gateway getGateway() {
		return gateway;
	}

	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	public Timestamp getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Timestamp dateCreate) {
		this.dateCreate = dateCreate;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
	/**
	 * Implementing many to one relation between gateway and peripheral
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "gateway_id")
		@JsonIgnore
	    private Gateway gateway;
	
	@Column(name = "date_created" )
	@CreationTimestamp
	private Timestamp dateCreate;
		
}
