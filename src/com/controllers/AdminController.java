package com.controllers;

import java.io.IOException;
import java.util.Date;

import javax.script.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Admin;
import com.models.GenericMethod;
import com.models.restResponse;
import com.services.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public int test() throws JsonParseException, JsonMappingException, IOException, ScriptException {
		String s = "function(a,b) a+b + 1";
		return test(2, 3, s);
	}
	
	@RequestMapping(value = "/addAdmin/{adminId}/{password}", method = RequestMethod.GET)
	public String addAdmin(@PathVariable("adminId") String adminId,@PathVariable("password") String password) {
	adminService.save(new Admin(adminId, password));
	return "admin added";
	}

	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public restResponse login(@RequestParam("userName") String name, @RequestParam("password") String password) {
		return adminService.login(name, password);
	}
	
	static int test(int a,int b,String s) throws JsonParseException, JsonMappingException, IOException, ScriptException {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		GenericMethod obj = (GenericMethod)engine.eval(s);
		return obj.operate(a, b);
		//return 0;
	}
}
