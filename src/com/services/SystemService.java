package com.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.models.SystemPC;
import com.models.restResponse;
import com.repositories.SystemRepo;

@Service
public class SystemService {
	
	@Autowired
	SystemRepo repo;
	
	
	public restResponse save(SystemPC system) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		if(system.dateOfPurchaseFromUI != null && !system.dateOfPurchaseFromUI.isEmpty()) {
			try {
				system.dateOfPurchase = format.parse(system.dateOfPurchaseFromUI);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else {
			system.dateOfPurchase = null;
		}
		try {
			SystemPC systemPc = repo.save(system);
			return new restResponse(200, systemPc, true);
		}catch(org.springframework.dao.DuplicateKeyException ex) {
			return new restResponse(101, null, true);
		}
		
		
	}
	
	public List<SystemPC> findByAllocation(boolean allocated){
		return repo.findByAllocated(allocated);
	}
	
	public SystemPC findBySystemId(String systemId) {
		return repo.findBySystemId(systemId);
	}
	
	@Transactional
	public boolean delete(SystemPC system){
		try {
			repo.removeBySystemId(system.systemId);
			return true;
		}catch(Exception ex) {
			return false;
		}
		
	}
	
	public List<SystemPC> findAll(){
		return repo.findAll();
	}
}
