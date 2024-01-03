package com.mmhb.farketmez.exception;

import org.hibernate.service.spi.ServiceException;

@SuppressWarnings("serial")
public class OperationNotAllowedException extends ServiceException {

	public OperationNotAllowedException(String message) {
		super(message);
	}
}