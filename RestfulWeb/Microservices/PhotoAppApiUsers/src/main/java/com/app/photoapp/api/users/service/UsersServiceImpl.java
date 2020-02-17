package com.app.photoapp.api.users.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.photoapp.api.users.data.UserEntity;
import com.app.photoapp.api.users.data.UsersRepository;
import com.app.photoapp.api.users.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {
	
	UsersRepository userRepository;
	
	@Autowired
	public UsersServiceImpl(UsersRepository userRepository) {
		this.userRepository=userRepository;
	}

	@Override
	public UserDto createUser(UserDto userDetails) {
		
		ModelMapper  modelMapper=new ModelMapper();		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		String userId=UUID.randomUUID().toString();
		userDetails.setUserId(userId);	
		
		UserEntity userEntity=modelMapper.map(userDetails, UserEntity.class);
		userEntity.setEncryptedPassword("test");
		
		
		UserEntity createdUser=userRepository.save(userEntity);
		
		UserDto userCreated=modelMapper.map(createdUser, UserDto.class);
		
		return userCreated;
	}

}
