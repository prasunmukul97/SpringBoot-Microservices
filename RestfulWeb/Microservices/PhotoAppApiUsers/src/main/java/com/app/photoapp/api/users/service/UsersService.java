package com.app.photoapp.api.users.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.app.photoapp.api.users.shared.UserDto;

public interface UsersService extends UserDetailsService{
	UserDto createUser(UserDto userDetails);
	List<UserDto> findByUserFirstName(UserDto userDetails);
	UserDto findByEmail(String username);

}
