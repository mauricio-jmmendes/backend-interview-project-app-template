package com.ninjaone.backendinterviewproject.model;

import com.ninjaone.backendinterviewproject.common.AppConstants.ServiceStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class ServiceOrder implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@NotBlank
	@Column(name = "service_type")
	private String serviceType;

	@NotBlank
	private String description;

	@NotNull
	@Positive
	private BigDecimal cost;

	@Column(name = "execution_date")
	private LocalDate executionDate;

	private ServiceStatus status;

	@ManyToOne
	@JoinColumn(name = "device_id", referencedColumnName = "id")
	private Device device;

	public ServiceOrder(String serviceType, String description, BigDecimal cost, ServiceStatus status) {
		this.serviceType = serviceType;
		this.description = description;
		this.cost = cost;
		this.status = status;
	}

	public ServiceOrder() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ServiceOrder)) {
			return false;
		}
		ServiceOrder that = (ServiceOrder) o;
		return id.equals(that.id)
					 && Objects.equals(serviceType, that.serviceType)
					 && description.equals(that.description)
					 && cost.equals(that.cost)
					 && Objects.equals(device, that.device);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, serviceType, description, cost, device);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", ServiceOrder.class.getSimpleName() + "[", "]")
				.add("id=" + id)
				.add("serviceType=" + serviceType)
				.add("description='" + description + "'")
				.add("cost=" + cost)
				.add("executionDate=" + executionDate)
				.add("status=" + status)
				.add("device=" + device)
				.toString();
	}
}
