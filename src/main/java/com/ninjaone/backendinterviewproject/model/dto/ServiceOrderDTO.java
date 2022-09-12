package com.ninjaone.backendinterviewproject.model.dto;

import com.ninjaone.backendinterviewproject.common.AppConstants.ServiceStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ServiceOrderDTO {

	private Long id;

	private String type;

	private String description;

	private BigDecimal cost;

	private LocalDate executionDate;

	private ServiceStatus status;

	private Long deviceId;

	public ServiceOrderDTO(Long id, String type, String description, BigDecimal cost, Long deviceId) {
		this.id = id;
		this.type = type;
		this.description = description;
		this.cost = cost;
		this.deviceId = deviceId;
	}

	public ServiceOrderDTO() {
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

	public LocalDate getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(LocalDate executionDate) {
		this.executionDate = executionDate;
	}

	public ServiceStatus getStatus() {
		return status;
	}

	public void setStatus(ServiceStatus status) {
		this.status = status;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
}
