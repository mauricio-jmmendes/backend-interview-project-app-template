package com.ninjaone.backendinterviewproject.controller.domain;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {

	private final Type type;
	private final String message;
	private String code;
	private List<Error> errors = new ArrayList<Error>();

	public ErrorResponse(Type type, String message) {
		this.type = type;
		this.message = message;
	}

	public ErrorResponse(Type type, String code, String message) {
		this.type = type;
		this.code = code;
		this.message = message;
	}

	public static ErrorResponse success(String text) {
		return new ErrorResponse(Type.SUCCESS, text);
	}

	public static ErrorResponse warning(String text) {
		return new ErrorResponse(Type.WARNING, text);
	}

	public static ErrorResponse danger(String text) {
		return new ErrorResponse(Type.DANGER, text);
	}

	public static ErrorResponse info(String text) {
		return new ErrorResponse(Type.INFO, text);
	}

	public String getMessage() {
		return message;
	}

	public Type getType() {
		return type;
	}

	public String getCode() {
		return code;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public void addError(String field, String code, String message) {
		this.errors.add(new Error(field, code, message));
	}

	public enum Type {
		SUCCESS, WARNING, DANGER, INFO
	}

	static class Error {

		private final String code;
		private final String message;
		private final String field;

		private Error(String field, String code, String message) {
			this.field = field;
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

		public String getField() {
			return field;
		}

	}
}
