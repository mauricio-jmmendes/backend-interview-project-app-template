package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.controller.domain.ErrorResponse;
import com.ninjaone.backendinterviewproject.controller.domain.ErrorResponse.Type;
import com.ninjaone.backendinterviewproject.exception.InvalidRequestException;
import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.context.MessageSource;
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

	private final MessageSource messageSource;

	public RestExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse(Type.DANGER, ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = {ResourceNotFoundException.class})
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = {InvalidRequestException.class})
	public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException ex, WebRequest req) {
		ErrorResponse errorResponse = new ErrorResponse(Type.DANGER, "INVALID_REQUEST", "request data is invalid.");
		BindingResult result = ex.getErrors();
		List<FieldError> fieldErrors = result.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			fieldErrors.forEach(e -> {
				errorResponse.addError(e.getField(), e.getCode(), e.getDefaultMessage());
			});
		}

		return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
