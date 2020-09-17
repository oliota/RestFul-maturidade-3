package com.oliota.restfull.maturidade3.model;

public class Request {
	private String url;
	private String contentType;
	private Object body;

	public Request() {

	}

	public Request(String url) {
		super();
		this.url = url; 
	}

	
	public Request(String url, String contentType) {
		super();
		this.url = url;
		this.contentType = contentType;
	}

	public Request(String url, String contentType, Object body) {
		super();
		this.url = url;
		this.contentType = contentType;
		this.body = body;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

}
