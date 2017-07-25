package com.fitbit.grad.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * property configuration associated with e-mail service requirements
 * 
 * @author nikos_mas, alex_kak
 */

@ConfigurationProperties("mailInfo")
public class MailInfoProperties {
	
	private String fileName;
	private String username;
	private String password;
	private String sendFrom;
	private String mailSmtpStartEnable;
	private String mailSmtpAuth;
	private String mailSmtpHost;
	private String mailSmtpPort;
	private String mailSubject;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSendFrom() {
		return sendFrom;
	}
	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}
	public String getMailSmtpStartEnable() {
		return mailSmtpStartEnable;
	}
	public void setMailSmtpStartEnable(String mailSmtpStartEnable) {
		this.mailSmtpStartEnable = mailSmtpStartEnable;
	}
	public String getMailSmtpAuth() {
		return mailSmtpAuth;
	}
	public void setMailSmtpAuth(String mailSmtpAuth) {
		this.mailSmtpAuth = mailSmtpAuth;
	}
	public String getMailSmtpHost() {
		return mailSmtpHost;
	}
	public void setMailSmtpHost(String mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
	}
	public String getMailSmtpPort() {
		return mailSmtpPort;
	}
	public void setMailSmtpPort(String mailSmtpPort) {
		this.mailSmtpPort = mailSmtpPort;
	}
	public String getMailSubject() {
		return mailSubject;
	}
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	
}
