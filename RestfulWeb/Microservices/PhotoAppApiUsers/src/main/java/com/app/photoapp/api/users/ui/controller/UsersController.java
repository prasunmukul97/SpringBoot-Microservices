package com.app.photoapp.api.users.ui.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.photoapp.api.users.service.UsersService;
import com.app.photoapp.api.users.shared.UserDto;
import com.app.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.app.photoapp.api.users.ui.model.CreateUserResponseModel;

@RestController
@RequestMapping(path="/users")
public class UsersController {
	
	@Autowired
	UsersService userservice;
	
	@Autowired
	private Environment env;
	
	@GetMapping("/status/check")
	public String status() {
		return "working at port number:"+env.getProperty("local.server.port");
	}
	
	@PostMapping
	public ResponseEntity<CreateUserResponseModel> createUser(@RequestBody CreateUserRequestModel userDetails) {
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto usrdto=modelMapper.map(userDetails,UserDto.class);
		UserDto createdUser=userservice.createUser(usrdto);
		CreateUserResponseModel returnValue=modelMapper.map(createdUser,CreateUserResponseModel.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}

}
