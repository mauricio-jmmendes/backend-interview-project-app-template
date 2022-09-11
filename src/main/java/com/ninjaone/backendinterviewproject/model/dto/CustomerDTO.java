package com.ninjaone.backendinterviewproject.model.dto;

import java.util.List;

public class CustomerDTO {

	private Long id;

	private String documentId;

	private String fullName;

	private String nickname;

	private String email;

	private List<DeviceDTO> devices;

	public CustomerDTO(String documentId, String fullName, String nickname, String email, List<DeviceDTO> devices) {
		this.documentId = documentId;
		this.fullName = fullName;
		this.nickname = nickname;
		this.email = email;
		this.devices = devices;
	}

	public CustomerDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<DeviceDTO> getDevices() {
		return devices;
	}

	public void setDevices(List<DeviceDTO> devices) {
		this.devices = devices;
	}
}
