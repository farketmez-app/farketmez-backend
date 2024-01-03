package com.mmhb.farketmez.exception;

@SuppressWarnings("serial")
public class DatabaseOperationException extends ServiceException {

	public DatabaseOperationException(String message) {
		super(message);
	}
}