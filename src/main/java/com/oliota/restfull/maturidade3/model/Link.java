package com.oliota.restfull.maturidade3.model;

public class Link {
	private String rel;
	private Request request;

	public Link() { 
	}

	public Link(String rel, Request request) {
		this.rel = rel;
		this.request = request; 
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

}
