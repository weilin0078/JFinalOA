package com.pointlion.plugin.mail.core;

import java.beans.PropertyEditorSupport;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * 
 * @author farmer
 *
 */
public class InternetAddressEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (hasText(text)) {
			try {
				setValue(new InternetAddress(text));
			}
			catch (AddressException ex) {
				throw new IllegalArgumentException("Could not parse mail address: " + ex.getMessage());
			}
		}
		else {
			setValue(null);
		}
	}

	@Override
	public String getAsText() {
		InternetAddress value = (InternetAddress) getValue();
		return (value != null ? value.toUnicodeString() : "");
	}
	
	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}
}