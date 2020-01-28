package com.app.ws.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.ws.exceptionHandler.UserServiceException;
import com.app.ws.ui.model.request.UserDetailsRequestModel;
import com.app.ws.ui.model.response.UserRest;
import com.app.ws.userservice.UserService;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {
	
	Map<String,UserRest> users=new HashMap<>();
	
	@Autowired
	@Qualifier("userservice1")	//mandatory when multiple implementation is present for the same interface.
	UserService userservice;
	
	/*@Autowired
	UserService userservice2;  
	//not allowed
	Field userservice2 in com.app.ws.ui.controller.UserController required a single bean, but 2 were found:
	- userServiceImpl:
	- userServiceImpl2:
	
	To resolve that above problem, we need @Qualifier annotation. 
	It is required to be present at service class as well as controller if the type of variable is declared as interface type.
	*/
	
	
	@GetMapping
	public String getUser(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "limit", defaultValue = "20") int limit) {
		return "get User was called with page=" + page + " and limit= " + limit;
	}
	
	@GetMapping("/allUsers")
	public ResponseEntity<List<UserRest>> getAllUsers(){	
		List<UserRest> userList=new ArrayList<>();
		users.forEach((k,v)->{			
			userList.add(v);
		});
		return new ResponseEntity<>(userList,HttpStatus.OK);
	}
	@GetMapping(path = "/{userId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRest> getUserById(@PathVariable String userId) {
		//Test of exception.
		if(true) throw new UserServiceException("An exception from user service controller:");
		String firstName=null;
		int firstNameLength=firstName.length();
		
		if(users.containsKey(userId)) {
			return new ResponseEntity<>(users.get(userId),HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}

	@PostMapping(consumes= {
			MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE
	},produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
		UserRest response=userservice.createUser(userDetails);				
		return new ResponseEntity<UserRest>(response,HttpStatus.OK);
	}


	@PutMapping(path="/{userId}",consumes= {
			MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE
	},produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserRest> updateUser(@RequestBody UserDetailsRequestModel userDetails,@PathVariable String userId) {
		UserRest user=users.get(userId);
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		users.put(userId, user);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}

	@DeleteMapping(path="/{userId}")
	public ResponseEntity deleteUser(@PathVariable String userId) {
		if(users.containsKey(userId))
			users.remove(userId);
		return ResponseEntity.noContent().build();
	}

}
