package com.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class Admin {

	@Id
	public String adminId;
	@Indexed(unique=true)
	public String name;
	public String sessionId;
	public Date timestramp;
	public String password;
	public Admin(String name,String password) {
		super();
		this.name = name;
		this.password = password;
	}
}
