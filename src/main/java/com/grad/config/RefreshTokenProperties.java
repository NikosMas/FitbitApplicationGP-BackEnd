package com.grad.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author nikos_mas
 */

@ConfigurationProperties("refreshtoken")
public class RefreshTokenProperties {
	
	private String headerAuth;
	private String grantType;
	private String headerAccept;
	
	public String getHeaderAuth() {
		return headerAuth;
	}
	public void setHeaderAuth(String headerAuth) {
		this.headerAuth = headerAuth;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getHeaderAccept() {
		return headerAccept;
	}
	public void setHeaderAccept(String headerAccept) {
		this.headerAccept = headerAccept;
	}
	@Override
	public String toString() {
		return "RefreshTokenProperties [headerAuth=" + headerAuth + ", grantType=" + grantType + ", headerAccept="
				+ headerAccept + "]";
	}
}
