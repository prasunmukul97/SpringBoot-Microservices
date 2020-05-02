package com.app.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.photoapp.api.users.data.UserEntity;
import com.app.photoapp.api.users.data.UsersRepository;
import com.app.photoapp.api.users.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {
	
	UsersRepository userRepository;
	@Autowired
	BCryptPasswordEncoder bencoder;
	
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
		userDetails.setEncryptedPassword(bencoder.encode(userDetails.getPassword()));		
		UserEntity userEntity=modelMapper.map(userDetails, UserEntity.class);	
		
		UserEntity createdUser=userRepository.save(userEntity);
		
		UserDto userCreated=modelMapper.map(createdUser, UserDto.class);
		
		return userCreated;
	}

	@Override
	public List<UserDto> findByUserFirstName(UserDto userDetails) {
		List<UserDto> userdtos=new ArrayList<>();
		String firstName=userDetails.getFirstName();
		List<UserEntity> listOfUsers=userRepository.findByFirstName(firstName);
		ModelMapper  modelMapper=new ModelMapper();		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		for(UserEntity userEntity:listOfUsers) {
			UserDto user=modelMapper.map(userEntity, UserDto.class);
			userdtos.add(user);
		}
		return userdtos;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username);
		if(userEntity==null) throw new  UsernameNotFoundException(username);
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),true,true,true,true,new ArrayList<>());
	}

	@Override
	public UserDto findByEmail(String email) throws UsernameNotFoundException {
		UserEntity userEntity=userRepository.findByEmail(email);
		if(userEntity==null) throw new  UsernameNotFoundException(email);
		ModelMapper  modelMapper=new ModelMapper();		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto user=modelMapper.map(userEntity, UserDto.class);		
		return user;
	}
	
	

}
