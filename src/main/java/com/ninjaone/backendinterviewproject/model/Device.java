package com.ninjaone.backendinterviewproject.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	@Column(name = "system_name")
	private String systemName;

	@Column(name = "device_type")
	private String deviceType;

	@OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ServiceOrder> services = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	private Customer customer;

	public Device(String systemName, String deviceType) {
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

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Set<ServiceOrder> getServices() {
		return services;
	}

	public void setServices(Set<ServiceOrder> services) {
		this.services = services;
	}

	public void addService(ServiceOrder serviceOrder) {
		this.services.add(serviceOrder);
		serviceOrder.setDevice(this);
	}

	public void removeService(ServiceOrder serviceOrder) {
		this.services.remove(serviceOrder);
		serviceOrder.setDevice(null);
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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
					 && Objects.equals(deviceType, device.deviceType);
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
