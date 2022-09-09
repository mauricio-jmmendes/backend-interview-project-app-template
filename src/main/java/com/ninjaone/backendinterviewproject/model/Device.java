package com.ninjaone.backendinterviewproject.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Device implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@NotBlank
	@Column(name = "system_name", unique = true)
	private String systemName;
	@Enumerated(EnumType.STRING)
	@Column(name = "device_type", unique = true)
	private DeviceType deviceType;

	@OneToMany(mappedBy = "device", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ServiceEntity> services = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName="id")
	private Customer customer;

	public Device(String systemName, DeviceType deviceType) {
		this.systemName = systemName;
		this.deviceType = deviceType;
	}

	public Device() {
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

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Device)) {
			return false;
		}
		Device device = (Device) o;
		return id.equals(device.id) && systemName.equals(device.systemName)
					 && deviceType == device.deviceType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, systemName, deviceType);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Device.class.getSimpleName() + "[", "]")
				.add("id='" + id + "'")
				.add("systemName='" + systemName + "'")
				.add("deviceType=" + deviceType)
				.toString();
	}
}
