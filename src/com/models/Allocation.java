package com.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class Allocation {

	@Id
	public String Id;
	public String allocationId;
	public String userName;
	public Date allocationDate;
	public String systemId;
	public boolean active;
	
}

