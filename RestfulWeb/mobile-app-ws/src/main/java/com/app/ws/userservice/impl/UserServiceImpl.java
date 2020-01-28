package com.app.ws.userservice.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.app.ws.shared.UsersUtility;
import com.app.ws.ui.model.request.UserDetailsRequestModel;
import com.app.ws.ui.model.response.UserRest;
import com.app.ws.userservice.UserService;

@Service
@Qualifier("userservice1") //its not mandatory if other service class does not implement same UserService interface
public class UserServiceImpl implements UserService {
	Map<String,UserRest> users;
	UsersUtility utility;	
	
	//@Autowired
	public UserServiceImpl() {
		System.out.println("Inside default constructor");
	}
	
	/*The below constructor will be called for autowiring.
    //If there are multiple occurances of @Autowired annotation, then error will come at the time of bean instantiation during server startup.
     * Error message: 
     * Found constructor with 'required' Autowired annotation already: public com.app.ws.userservice.impl.UserServiceImpl()
     * 
     * Below is the success message during server startup:
	2020-01-28 20:49:44.991  INFO 11396 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1044 ms
	Inside 1 arg constructor
	2020-01-28 20:49:45.261  INFO 11396 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
	202
	
	If one argument constructor is not autowired, then default constructor will be called.
	*/
	
	@Autowired
	public UserServiceImpl(UsersUtility utility) { 
		this.utility=utility;
		System.out.println("Inside 1 arg constructor");
	}
	
	@Override
	public UserRest createUser(UserDetailsRequestModel userDetails) {
		
		UserRest returnVal=new UserRest();
		returnVal.setFirstName(userDetails.getFirstName());
		returnVal.setLastName(userDetails.getLastName());
		returnVal.setEmail(userDetails.getEmail());
		String userId=utility.generateId();
		returnVal.setUserId(userId);
		if(users==null) users=new HashMap<>();
		users.put(userId, returnVal);
		return returnVal;
		
	}

}
