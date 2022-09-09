package com.ninjaone.backendinterviewproject.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "authority")
public class Authority implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@NaturalId
	@Column(length = 60)
	private AuthType type;

	public Authority() {
	}

	public Authority(AuthType type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AuthType getType() {
		return type;
	}

	public void setType(AuthType name) {
		this.type = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Authority)) {
			return false;
		}
		Authority authority = (Authority) o;
		return id.equals(authority.id) && type == authority.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, type);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Authority.class.getSimpleName() + "[", "]")
				.add("id=" + id)
				.add("type=" + type)
				.toString();
	}
}
