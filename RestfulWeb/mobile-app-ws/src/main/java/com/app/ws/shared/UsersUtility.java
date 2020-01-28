package com.app.ws.shared;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class UsersUtility {
	
	public String generateId() {
		return UUID.randomUUID().toString();
	}

}
