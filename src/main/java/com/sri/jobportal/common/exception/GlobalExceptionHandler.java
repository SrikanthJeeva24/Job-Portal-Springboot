package com.sri.jobportal.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
 
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
		
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult()
		.getFieldErrors()
		.forEach(error -> {
		     errors.put(error.getField(), error.getDefaultMessage());	
		});
		return errors;
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handleUserNotFoundException(UserNotFoundException ex) {
		
		Map<String, String> error = new HashMap<>();
		
		error.put("message:", ex.getMessage());
		
		return error;
	}
	
	@ExceptionHandler(DuplicateResourceException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Map<String, String> handleDuplicateResourceException(
	        DuplicateResourceException ex) {

	    Map<String, String> error = new HashMap<>();

	    error.put("message", ex.getMessage());

	    return error;
	}
	
	
	@ExceptionHandler(UnAuthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Map<String, String> handleUnAuthorizedException(
			UnAuthorizedException ex) {

	    Map<String, String> error = new HashMap<>();

	    error.put("message", ex.getMessage());

	    return error;
	}
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handleResourceNotFoundException(
			ResourceNotFoundException ex) {

	    Map<String, String> error = new HashMap<>();

	    error.put("message", ex.getMessage());

	    return error;
	}
	
}

