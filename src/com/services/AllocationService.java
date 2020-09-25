package com.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.models.Allocation;
import com.models.SystemPC;
import com.models.restResponse;
import com.repositories.AllocationRepo;

@Service
public class AllocationService {

	@Autowired
	AllocationRepo allocationRepo;
	
	@Autowired
	SystemService systemService;
	
	public restResponse save(Allocation allocation,boolean allocate) {
		allocation.allocationDate = new Date();
		SystemPC system = systemService.findBySystemId(allocation.systemId);
		if(system!=null) {
			allocation.active = allocate;
			system.allocated = allocate;
			if(allocate) {
				allocation.allocationId = allocation.userName+"_"+allocation.systemId;
				allocation = allocationRepo.save(allocation);
				system.latestAllocation = allocation;
			}
			else {
				system.allocations.add(system.latestAllocation);
				system.latestAllocation = null;
			}
			systemService.save(system);
			return new restResponse(200, allocation,system, true);
		}
		allocation.active = allocation.active ? false : true;
		return new restResponse(200, allocation,system, true);
	}
	
//	@Transactional
//	public boolean delete(Allocation allocation){
//		try {
//			allocationRepo.removeByAllocationId(allocationId)
//			return true;
//		}catch(Exception ex) {
//			return false;
//		}
//		
//	}
	
	public Allocation findBySystemIdAndActive(String systemId,boolean active) {
		return allocationRepo.findBySystemIdAndActive(systemId, active);
	}
}
