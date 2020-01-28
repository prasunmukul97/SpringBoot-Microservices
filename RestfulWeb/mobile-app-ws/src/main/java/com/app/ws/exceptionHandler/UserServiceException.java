package com.app.ws.exceptionHandler;

public class UserServiceException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6135232604221146308L;

	public UserServiceException(String message) {
		super(message);
	}

}
