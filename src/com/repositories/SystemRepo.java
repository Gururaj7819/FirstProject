package com.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.models.SystemPC;

public interface SystemRepo extends MongoRepository<SystemPC, String>{

	public List<SystemPC> findByAllocated(boolean allocated);
	
	public SystemPC findBySystemId(String systemId);
	
	public SystemPC removeBySystemId(String systemId);
	
	
	
}
