package com.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.models.Admin;

public interface AdminRepo extends MongoRepository<Admin, String>{

	public Admin findByNameAndPassword(String name,String password);
	public Admin findByNameAndSessionId(String name,String sessionId);
	
	//https://www.baeldung.com/queries-in-spring-data-mongodb
}
