package com.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemPC {

	@Id
	public String Id;
	@Indexed(unique=true)
	public String systemId;
	public String model;
	public boolean allocated;
	public String os;
	public Date dateOfPurchase;
	@Transient
	public String dateOfPurchaseFromUI;
	public Allocation latestAllocation;
	public List<Allocation> allocations = new ArrayList<Allocation>();
	
}
