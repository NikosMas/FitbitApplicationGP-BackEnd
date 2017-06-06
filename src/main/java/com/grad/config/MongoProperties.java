package com.grad.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * property configuration associated with Mongo database requirements
 * 
 * @author nikos_mas, alex_kak
 */

@ConfigurationProperties("mongoDB")
public class MongoProperties {
	
	private String dbname;
	private String host;
	private Integer port;
	
	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	@Override
	public String toString() {
		return "MongoProperties [dbname=" + dbname + ", host=" + host + ", port=" + port + "]";
	}
	
}
