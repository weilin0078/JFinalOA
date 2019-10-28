package com.pointlion.plugin.mail.core;

import java.util.Date;

import com.pointlion.plugin.mail.exception.MailParseException;

/**
 * 
 * @author farmer
 *
 */
public interface MailMessage {

	public void setFrom(String from) throws MailParseException;

	public void setReplyTo(String replyTo) throws MailParseException;

	public void setTo(String to) throws MailParseException;

	public void setTo(String[] to) throws MailParseException;

	public void setCc(String cc) throws MailParseException;

	public void setCc(String[] cc) throws MailParseException;

	public void setBcc(String bcc) throws MailParseException;

	public void setBcc(String[] bcc) throws MailParseException;

	public void setSentDate(Date sentDate) throws MailParseException;

	public void setSubject(String subject) throws MailParseException;

	public void setText(String text) throws MailParseException;

}
