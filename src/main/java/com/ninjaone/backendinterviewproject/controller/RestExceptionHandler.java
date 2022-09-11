package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.controller.domain.ErrorResponse;
import com.ninjaone.backendinterviewproject.controller.domain.ErrorResponse.Type;
import com.ninjaone.backendinterviewproject.exception.InvalidRequestException;
import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import java.util.function.Consumer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private Consumer<FieldError> mapToError(ErrorResponse errorResponse) {
		return e -> errorResponse.addError(e.getField(), e.getCode(), e.getDefaultMessage());
	}

	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse(Type.DANGER, ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = {ResourceNotFoundException.class})
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(Type.DANGER, "RESOURCE_NOT_FOUND", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = {InvalidRequestException.class})
	public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException ex, WebRequest req) {
		ErrorResponse errorResponse = new ErrorResponse(Type.DANGER, "INVALID_REQUEST", ex.getMessage());
		BindingResult result = ex.getErrors();
		result.getFieldErrors().forEach(mapToError(errorResponse));
		return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
	}
}
