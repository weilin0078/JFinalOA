package com.pointlion.plugin.mail.exception;

/**
 * 
 * @author farmer
 *
 */
public class MailParseException extends MailException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8089227557387828745L;

	/**
	 * Constructor for MailParseException.
	 * @param msg the detail message
	 */
	public MailParseException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for MailParseException.
	 * @param msg the detail message
	 * @param cause the root cause from the mail API in use
	 */
	public MailParseException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Constructor for MailParseException.
	 * @param cause the root cause from the mail API in use
	 */
	public MailParseException(Throwable cause) {
		super("Could not parse mail", cause);
	}

}
