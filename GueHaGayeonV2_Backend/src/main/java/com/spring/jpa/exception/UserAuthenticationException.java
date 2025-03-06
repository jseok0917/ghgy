package com.spring.jpa.exception;

public class UserAuthenticationException extends Exception {
	
	public UserAuthenticationException() {
		super();
	}
	
	public UserAuthenticationException(String message) {
		super(message);
	}
	
}
