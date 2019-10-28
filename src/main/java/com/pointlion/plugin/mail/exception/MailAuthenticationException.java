package com.pointlion.plugin.mail.exception;

/**
 * 
 * @author farmer
 *
 */
public class MailAuthenticationException extends MailException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5675296894245634470L;

	/**
	 * Constructor for MailAuthenticationException.
	 * @param msg message
	 */
	public MailAuthenticationException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for MailAuthenticationException.
	 * @param msg the detail message
	 * @param cause the root cause from the mail API in use
	 */
	public MailAuthenticationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Constructor for MailAuthenticationException.
	 * @param cause the root cause from the mail API in use
	 */
	public MailAuthenticationException(Throwable cause) {
		super("Authentication failed", cause);
	}

}
