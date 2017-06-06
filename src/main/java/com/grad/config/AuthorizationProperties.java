package com.grad.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * property configuration associated with access token service requirements
 * 
 * @author nikos_mas, alex_kak
 */

@ConfigurationProperties("accesstoken")
public class AuthorizationProperties {
	
	private String grantType;
	private String redirectUri;
	private String headerAccept;
	private String authCodeUri1;
	private String authCodeUri2;
	
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
	public String getHeaderAccept() {
		return headerAccept;
	}
	public void setHeaderAccept(String headerAccept) {
		this.headerAccept = headerAccept;
	}
	public String getAuthCodeUri1() {
		return authCodeUri1;
	}
	public void setAuthCodeUri1(String authCodeUri1) {
		this.authCodeUri1 = authCodeUri1;
	}
	public String getAuthCodeUri2() {
		return authCodeUri2;
	}
	public void setAuthCodeUri2(String authCodeUri2) {
		this.authCodeUri2 = authCodeUri2;
	}
	@Override
	public String toString() {
		return "AuthorizationProperties [grantType=" + grantType + ", redirectUri=" + redirectUri + ", headerAccept="
				+ headerAccept + ", authCodeUri1=" + authCodeUri1 + ", authCodeUri2=" + authCodeUri2 + "]";
	}
}
