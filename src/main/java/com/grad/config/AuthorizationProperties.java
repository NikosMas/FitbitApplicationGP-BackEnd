package com.grad.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author nikos_mas
 */

@ConfigurationProperties("accesstoken")
public class AuthorizationProperties {
	
	private String clientid;
	private String grantType;
	private String redirectUri;
	private String headerAuth;
	private String headerAccept;
	private String authCodeUri;
	
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
	public String getAuthCodeUri() {
		return authCodeUri;
	}
	public void setAuthCodeUri(String authCodeUri) {
		this.authCodeUri = authCodeUri;
	}
	@Override
	public String toString() {
		return "AuthorizationProperties [clientid=" + clientid + ", grantType=" + grantType + ", redirectUri="
				+ redirectUri + ", headerAuth=" + headerAuth + ", headerAccept=" + headerAccept + ", authCodeUri="
				+ authCodeUri + "]";
	}
	
}
