package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.models.Allocation;
import com.models.SystemPC;
import com.models.restResponse;
import com.repositories.AdminRepo;
import com.services.AdminService;
import com.services.SystemService;

@RestController
@RequestMapping("/system")
public class SystemController {

	@Autowired
	SystemService systemService;

	@Autowired
	AdminService adminService;

	@RequestMapping(value = "/getAllSystems", method = RequestMethod.GET)
	public restResponse getAllSystems(@RequestParam("userName") String userName, @RequestParam("sessionId") String sessionId) {
		boolean validated = adminService.validateUser(userName, sessionId);
		if (validated) {

			return new restResponse(200, systemService.findAll(), true);
		} else {
			return new restResponse(-1, null, false);
		}
	}
	
	@RequestMapping(value = "/findByAllocation", method = RequestMethod.GET)
	public restResponse findByAllocation(@RequestParam("userName") String userName, @RequestParam("sessionId") String sessionId,@RequestParam("allocated") boolean allocated) {
		boolean validated = adminService.validateUser(userName, sessionId);
		if (validated) {
			return new restResponse(200, systemService.findByAllocation(allocated),true);
		} else {
			return new restResponse(-1, null, false);
		}
	}

	@RequestMapping(value = "/saveSystem", method = RequestMethod.POST, consumes="application/json")
	public restResponse saveSystem(@RequestParam("userName") String userName,
			@RequestParam("sessionId") String sessionId, @RequestBody SystemPC system) {
		boolean validated = adminService.validateUser(userName, sessionId);
		if (validated) {
			return systemService.save(system);
			
		}
		return new restResponse(-1, null, false);
	}
	
	@RequestMapping(value = "/deleteSystem", method = RequestMethod.POST, consumes="application/json")
	public restResponse deleteSystem(@RequestParam("userName") String userName,
			@RequestParam("sessionId") String sessionId, @RequestBody SystemPC system) {
		boolean validated = adminService.validateUser(userName, sessionId);
		if (validated) {
			boolean ret = systemService.delete(system);
			if(ret) {
				return new restResponse(200,null,true);
			}
			
		}else {
			return new restResponse(-1, null, false);
		}
		return new restResponse(-101, null, false);
	}

}
