package com.ninjaone.backendinterviewproject.model.dto;

import java.math.BigDecimal;

public class ServiceDTO {

	private Long id;
	private String type;
	private String description;
	private BigDecimal cost;

	public ServiceDTO(Long id, String type, String description, BigDecimal cost) {
		this.id = id;
		this.type = type;
		this.description = description;
		this.cost = cost;
	}

	public ServiceDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}


}
