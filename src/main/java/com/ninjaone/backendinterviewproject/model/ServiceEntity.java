package com.ninjaone.backendinterviewproject.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "service")
public class ServiceEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@NotBlank
	@Enumerated(EnumType.STRING)
	@Column(name = "service_type")
	private ServiceType serviceType;

	@NotBlank
	private String description;

	@NotNull
	@Positive
	private BigDecimal cost;

	@Column(name = "execution_date")
	private Date executionDate;

	private Status status;

	@ManyToOne
	@JoinColumn(name = "device_id", referencedColumnName = "id")
	private Device device;

	public ServiceEntity(ServiceType serviceType, String description, BigDecimal cost) {
		this.serviceType = serviceType;
		this.description = description;
		this.cost = cost;
	}

	public ServiceEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
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

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Date getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ServiceEntity)) {
			return false;
		}
		ServiceEntity that = (ServiceEntity) o;
		return id.equals(that.id) && serviceType == that.serviceType && description.equals(that.description) && cost.equals(that.cost) &&
					 Objects.equals(device, that.device);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, serviceType, description, cost, device);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", ServiceEntity.class.getSimpleName() + "[", "]")
				.add("id=" + id)
				.add("serviceType=" + serviceType)
				.add("description='" + description + "'")
				.add("cost=" + cost)
				.add("executionDate=" + executionDate)
				.add("status=" + status)
				.add("device=" + device)
				.toString();
	}

	public enum Status {
		PENDING, DONE
	}
}
