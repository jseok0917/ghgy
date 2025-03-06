package com.spring.jpa.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DMLException extends RuntimeException{
	private String title;
	private String message;
	private HttpStatus httpStatus;

	
	
	public DMLException(String title, String message) {
		this(title, message, HttpStatus.EXPECTATION_FAILED);
	}
	
}
