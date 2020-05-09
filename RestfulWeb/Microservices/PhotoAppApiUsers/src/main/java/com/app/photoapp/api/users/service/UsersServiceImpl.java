package com.app.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.photoapp.api.users.data.AlbumsServiceClient;
import com.app.photoapp.api.users.data.UserEntity;
import com.app.photoapp.api.users.data.UsersRepository;
import com.app.photoapp.api.users.exception.UsersServiceException;
import com.app.photoapp.api.users.shared.UserDto;
import com.app.photoapp.api.users.ui.model.AlbumResponseModel;

import feign.FeignException;

@Service
public class UsersServiceImpl implements UsersService {
	
	UsersRepository userRepository;	
	BCryptPasswordEncoder bencoder;	
	//RestTemplate restTemplate;
	Environment env;
	AlbumsServiceClient albumServiceClient;
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public UsersServiceImpl(UsersRepository userRepository,BCryptPasswordEncoder bencoder,AlbumsServiceClient albumServiceClient,Environment env) {
		this.userRepository=userRepository;
		this.bencoder=bencoder;
		//this.restTemplate=restTemplate;
		this.albumServiceClient=albumServiceClient;
		this.env=env;
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
	public UserDto findByEmail(String email) {
		UserEntity userEntity=userRepository.findByEmail(email);
		if(userEntity==null) throw new  UsernameNotFoundException(email);
		ModelMapper  modelMapper=new ModelMapper();		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto user=modelMapper.map(userEntity, UserDto.class);		
		return user;
	}

	@Override
	public UserDto getUserByUserId(String userId) throws UsersServiceException{
		UserEntity findByUserId = userRepository.findByUserId(userId);
		if(findByUserId==null ) throw new UsersServiceException("user not found");		
		UserDto userDto=new ModelMapper().map(findByUserId,UserDto.class);
		
		//Use of RestTemplate exchange method to hit request to albums microservice from users microservice.
		/*
		String albumUrl=String.format(env.getProperty("albums.url"), userId);
		ResponseEntity<List<AlbumResponseModel>> albumListResponse = restTemplate.exchange(albumUrl, HttpMethod.GET,null,new ParameterizedTypeReference<List<AlbumResponseModel>>() {
		});
		List<AlbumResponseModel> albumList = albumListResponse.getBody();
		*/ 
		//Use of Feign Client
		
		//Handing Feign Exception using try and catch.
//		List<AlbumResponseModel> albumList=null;
//		try {
//			albumList = albumServiceClient.getAlbums(userId);
//		} catch (FeignException e) {
//			logger.error(e.getMessage());
//		}
		
		//Handling Feign exception with Feign Error decoder.
		List<AlbumResponseModel> albumList=albumServiceClient.getAlbums(userId);
		userDto.setAlbums(albumList);
		return userDto;
	}
	
	

}
