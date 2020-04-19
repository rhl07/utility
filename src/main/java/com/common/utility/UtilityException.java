package com.common.utility;

public class UtilityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 

	public UtilityException(String message) {
		super(message);
	}
	
	
	public UtilityException(String message, Throwable throwable){
		super(message, throwable);
	}

	public static void throwErr(String message){
		throw new UtilityException(message); 
	}
}
