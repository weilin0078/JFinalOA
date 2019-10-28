package com.pointlion.plugin.mail.exception;

/**
 * 
 * @author farmer
 *
 */
public class MailPreparationException extends MailException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2430875949743255277L;

	/**
	 * Constructor for MailPreparationException.
	 * @param msg the detail message
	 */
	public MailPreparationException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for MailPreparationException.
	 * @param msg the detail message
	 * @param cause the root cause from the mail API in use
	 */
	public MailPreparationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MailPreparationException(Throwable cause) {
		super("Could not prepare mail", cause);
	}

}
