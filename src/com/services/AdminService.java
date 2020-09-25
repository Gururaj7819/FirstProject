package com.services;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.models.Admin;
import com.models.restResponse;
import com.repositories.AdminRepo;

@Service
public class AdminService {

	@Autowired
	AdminRepo adminRepo;
	
	private static final int ONE_HOUR = 60 * 60 * 1000;
	
	public Admin save (Admin admin) {
		adminRepo.save(admin);
		return admin;
	}
	
	protected String gerRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	
	public restResponse login(String name,String password) {
		Admin admin = adminRepo.findByNameAndPassword(name, password);
		if(admin!=null) {
			admin.sessionId = gerRandomString();
			admin.timestramp = new Date();
			admin = adminRepo.save(admin);
			return new restResponse(200, admin.sessionId, admin.name, true);
		}else {
			return new restResponse(-1, null,null, false);
		}
	}
	
	public boolean validateUser(String userName,String sessionId) {
		Admin admin = adminRepo.findByNameAndSessionId(userName, sessionId);
		if(admin == null) {
			return false;
		}
		long currentTimeStramp = new Date().getTime();
		long oldTimestramp = admin.timestramp.getTime();
		
		long diff = (currentTimeStramp - oldTimestramp)/(60 * 60 * 1000) % 24;
		
		if(diff >= 1) {
			return false;
		}
		admin.timestramp = new Date();
		adminRepo.save(admin);

		return true;
		
		
	}
}
