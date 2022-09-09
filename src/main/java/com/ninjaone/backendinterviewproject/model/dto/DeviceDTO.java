package com.ninjaone.backendinterviewproject.model.dto;

public class DeviceDTO {

	private String id;
	private String systemName;
	private String type;

	public DeviceDTO(String id, String systemName, String type) {
		this.id = id;
		this.systemName = systemName;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

}
