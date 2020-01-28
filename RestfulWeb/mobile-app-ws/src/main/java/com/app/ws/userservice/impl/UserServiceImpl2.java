package com.app.ws.userservice.impl;

import org.springframework.stereotype.Service;

import com.app.ws.ui.model.request.UserDetailsRequestModel;
import com.app.ws.ui.model.response.UserRest;
import com.app.ws.userservice.UserService;

@Service
public class UserServiceImpl2 implements UserService {

	@Override
	public UserRest createUser(UserDetailsRequestModel userDetails) {
		UserRest resp=new UserRest();
		System.out.println("Inside 2nd service");
		return resp;
	}

}
