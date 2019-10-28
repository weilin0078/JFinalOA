package com.pointlion.plugin.mail.core;

import com.pointlion.plugin.mail.exception.MailException;

/**
 * 
 * @author farmer
 *
 */
public interface MailSender {
	
	/**
	 * Send the given simple mail message.
	 * @param simpleMessage the message to send
	 * @throws org.springframework.mail.MailParseException
	 * in case of failure when parsing the message
	 * @throws org.springframework.mail.MailAuthenticationException
	 * in case of authentication failure
	 * @throws org.springframework.mail.MailSendException
	 * in case of failure when sending the message
	 */
	void send(SimpleMailMessage simpleMessage) throws MailException;

	/**
	 * Send the given array of simple mail messages in batch.
	 * @param simpleMessages the messages to send
	 * @throws org.springframework.mail.MailParseException
	 * in case of failure when parsing a message
	 * @throws org.springframework.mail.MailAuthenticationException
	 * in case of authentication failure
	 * @throws org.springframework.mail.MailSendException
	 * in case of failure when sending a message
	 */
	void send(SimpleMailMessage[] simpleMessages) throws MailException;

}
