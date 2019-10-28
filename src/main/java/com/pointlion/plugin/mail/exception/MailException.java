package com.pointlion.plugin.mail.exception;


/**
 * 
 * @author farmer
 *
 */
public abstract class MailException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2074009899265906631L;

	/**
	 * Constructor for MailException.
	 * @param msg the detail message
	 */
	public MailException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for MailException.
	 * @param msg the detail message
	 * @param cause the root cause from the mail API in use
	 */
	public MailException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
