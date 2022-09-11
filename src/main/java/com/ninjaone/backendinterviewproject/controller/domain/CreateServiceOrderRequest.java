package com.ninjaone.backendinterviewproject.controller.domain;

import com.ninjaone.backendinterviewproject.common.AppConstants.ServiceStatus;
import java.math.BigDecimal;

public class CreateServiceOrderRequest {

	private Long deviceId;

	private String type;

	private String description;

	private BigDecimal cost;

	private ServiceStatus status;

	public CreateServiceOrderRequest(Long deviceId, String type, String description, BigDecimal cost, ServiceStatus status) {
		this.deviceId = deviceId;
		this.type = type;
		this.description = description;
		this.cost = cost;
		this.status = status;
	}

	public CreateServiceOrderRequest() {
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
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

	public ServiceStatus getStatus() {
		return status;
	}

	public void setStatus(ServiceStatus status) {
		this.status = status;
	}
}
