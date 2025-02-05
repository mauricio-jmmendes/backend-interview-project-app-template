package com.ninjaone.backendinterviewproject.exception;

import java.io.Serializable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private final String resource;
	private final String field;
	private final Serializable value;

	public ResourceNotFoundException(String resource, String field, Object value) {
		super(String.format("%s not found with %s : '%s'", resource, field, value));
		this.resource = resource;
		this.field = field;
		this.value = (Serializable) value;
	}

	public String getResource() {
		return resource;
	}

	public String getField() {
		return field;
	}

	public Object getValue() {
		return value;
	}
}
