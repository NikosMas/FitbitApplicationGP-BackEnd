package com.grad.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author nikos_mas
 */

@ConfigurationProperties("refreshtoken")
public class RefreshTokenProperties {

	private String grantType;
	private String headerAccept;

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
		return "RefreshTokenProperties [grantType=" + grantType + ", headerAccept=" + headerAccept + "]";
	}
}
