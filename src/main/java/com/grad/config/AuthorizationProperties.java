package com.grad.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("accesstoken")
public class AuthorizationProperties {
	
	private String clientid;
	private String grantType;
	private String redirectUri;
	private String headerAuth;
	private String headerAccept;
	
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	public String getHeaderAuth() {
		return headerAuth;
	}
	public void setHeaderAuth(String headerAuth) {
		this.headerAuth = headerAuth;
	}
	public String getHeaderAccept() {
		return headerAccept;
	}
	public void setHeaderAccept(String headerAccept) {
		this.headerAccept = headerAccept;
	}
	
}
