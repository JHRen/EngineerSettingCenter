package com.amkor.util.mail.picmail;

import java.util.Map;

public class Email {
	String[] toEmails;
	String[] bccEmails;
	String[] ccEmails;
	String subject;
	String Content;
	String[] attachments;
	Map<String, String> imagesMap;

	public String[] getToEmails() {
		return toEmails;
	}

	public void setToEmails(String[] toEmails) {
		this.toEmails = toEmails;
	}

	public String[] getBccEmails() {
		return bccEmails;
	}

	public void setBccEmails(String[] bccEmails) {
		this.bccEmails = bccEmails;
	}

	public String[] getCcEmails() {
		return ccEmails;
	}

	public void setCcEmails(String[] ccEmails) {
		this.ccEmails = ccEmails;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String[] getAttachments() {
		return attachments;
	}

	public void setAttachments(String[] attachments) {
		this.attachments = attachments;
	}

	public Map<String, String> getImagesMap() {
		return imagesMap;
	}

	public void setImagesMap(Map<String, String> imagesMap) {
		this.imagesMap = imagesMap;
	}

}
