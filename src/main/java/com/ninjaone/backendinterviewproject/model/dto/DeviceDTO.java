package com.ninjaone.backendinterviewproject.model.dto;

import java.util.List;

public class DeviceDTO {

	private Long id;

	private String systemName;

	private String type;

	private List<ServiceDTO> purchasedServices;

	public DeviceDTO(Long id, String systemName, String type, List<ServiceDTO> purchasedServices) {
		this.id = id;
		this.systemName = systemName;
		this.type = type;
		this.purchasedServices = purchasedServices;
	}

	public DeviceDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<ServiceDTO> getPurchasedServices() {
		return purchasedServices;
	}

	public void setPurchasedServices(List<ServiceDTO> purchasedServices) {
		this.purchasedServices = purchasedServices;
	}
}
