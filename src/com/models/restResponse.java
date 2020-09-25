package com.models;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


public class restResponse {
	
	public int status;
	public String sessionId;
	public String userName;
	public Object data;
	public Object addldata;
	public boolean success;
	
	public restResponse() {
		
	}
	public restResponse(int status,Object data, boolean success) {
		super();
		this.status = status;
		this.data = data;
		this.success = success;
	}
	
	public restResponse(int status,Object data, Object addldata,boolean success) {
		super();
		this.status = status;
		this.data = data;
		this.addldata = addldata;
		this.success = success;
	}
	
	public restResponse(int status, String sessionId,String userName, boolean success) {
		super();
		this.status = status;
		this.sessionId = sessionId;
		this.userName = userName;
		this.success = success;
	}
	
}
