package com.ninjaone.backendinterviewproject.model;

import java.io.Serializable;
import java.util.Collections;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@NaturalId
	@NotBlank
	@Size(max = 20)
	@Column(name = "document_id", unique = true)
	private String documentId;

	@NotBlank
	@Size(max = 50)
	@Column(name = "full_name")
	private String fullName;

	@NotBlank
	@Size(max = 20)
	@Column(unique = true)
	private String nickname;

	@NotBlank
	@Size(max = 64)
	@Email
	private String email;

	@NotBlank
	@Size(max = 128)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "customer_authorities", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
	private Set<Authority> authorities = new HashSet<>();

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Device> devices = new HashSet<>();

	public Customer(String documentId, String fullName, String nickname, String email, String password, Set<Authority> authorities) {
		this.documentId = documentId;
		this.fullName = fullName;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public Customer(String documentId, String fullName, String nickname, String email, String password) {
		this(documentId, fullName, nickname, email, password, Collections.emptySet());
	}

	public Customer() {
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

	public void setDocumentId(String customerId) {
		this.documentId = customerId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String name) {
		this.fullName = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String username) {
		this.nickname = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public void addAuthority(Authority authority) {
		this.authorities.add(authority);
	}

	public void removeAuthority(Authority authority) {
		this.authorities.remove(authority);
	}

	public Set<Device> getDevices() {
		return devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	public void addDevice(Device device) {
		this.devices.add(device);
	}

	public void removeDevice(Device device) {
		this.devices.remove(device);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Customer)) {
			return false;
		}
		Customer customer = (Customer) o;
		return id.equals(customer.id) && documentId.equals(customer.documentId) && fullName.equals(
				customer.fullName) && nickname.equals(customer.nickname) && email.equals(customer.email)
					 && password.equals(customer.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, documentId, fullName, nickname, email, password);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Customer.class.getSimpleName() + "[", "]")
				.add("id=" + id)
				.add("documentId='" + documentId + "'")
				.add("fullName='" + fullName + "'")
				.add("nickname='" + nickname + "'")
				.add("email='" + email + "'")
				.add("password='" + password + "'")
				.add("authorities=" + authorities)
				.add("devices=" + devices)
				.toString();
	}
}