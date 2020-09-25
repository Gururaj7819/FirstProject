package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.models.Allocation;
import com.models.restResponse;
import com.services.AdminService;
import com.services.AllocationService;

@RestController
@RequestMapping("/allocation")
public class AllocationController {

	@Autowired
	AllocationService allocationService;
	
	@Autowired
	AdminService adminService;
	
	@RequestMapping(value = "/allocate", method = RequestMethod.POST, consumes = "application/json")
	public restResponse allocate(@RequestParam("userName") String userName,
											@RequestParam("sessionId") String sessionId,
											@RequestBody Allocation allocation) {
		
		boolean validated = adminService.validateUser(userName, sessionId);
		if(validated) {
			return allocationService.save(allocation,true);
			
		}else {
			return new restResponse(-1,null,false);
		}
	}
	
	@RequestMapping(value = "/deallocate", method = RequestMethod.POST)
	public restResponse deAllocate(@RequestParam("userName") String userName,
											@RequestParam("sessionId") String sessionId,
											@RequestBody Allocation allocation) {
		
		boolean validated = adminService.validateUser(userName, sessionId);
		if(validated) {
			return allocationService.save(allocation,false);
		}else {
			return new restResponse(-1,null,false);
		}
	}
}
