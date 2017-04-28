package com.grad.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author nikos_mas
 */

@ConfigurationProperties("mailInfo")
public class MailInfoProperties {
	
	private String fileName;
	private String username;
	private String password;
	private String sendFrom;
	
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
	@Override
	public String toString() {
		return "MailInfoProperties [fileName=" + fileName + ", username=" + username + ", password=" + password
				+ ", sendFrom=" + sendFrom + "]";
	}
	
}
