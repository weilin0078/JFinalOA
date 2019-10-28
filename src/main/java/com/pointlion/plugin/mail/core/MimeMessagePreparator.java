package com.pointlion.plugin.mail.core;

import javax.mail.internet.MimeMessage;

/**
 * 
 * @author farmer
 *
 */
public interface MimeMessagePreparator {

	/**
	 * Prepare the given new MimeMessage instance.
	 * @param mimeMessage the message to prepare
	 * @throws javax.mail.MessagingException passing any exceptions thrown by MimeMessage
	 * methods through for automatic conversion to the MailException hierarchy
	 * @throws java.io.IOException passing any exceptions thrown by MimeMessage methods
	 * through for automatic conversion to the MailException hierarchy
	 * @throws Exception if mail preparation failed, for example when a
	 * Velocity template cannot be rendered for the mail text
	 */
	void prepare(MimeMessage mimeMessage) throws Exception;

}
