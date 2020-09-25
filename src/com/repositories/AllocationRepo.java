package com.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.models.Allocation;
import com.models.SystemPC;

public interface AllocationRepo extends MongoRepository<Allocation, String>{

	public Allocation findBySystemIdAndActive(String systemId,boolean active);
	
	public Allocation removeByAllocationId(String allocationId);
	
}
