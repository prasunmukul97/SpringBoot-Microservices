package com.app.photoapp.api.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.photoapp.api.users.service.UsersService;
import com.app.photoapp.api.users.shared.UserDto;
import com.app.photoapp.api.users.ui.model.LoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private UsersService userService;
	private Environment env;
	
	public AuthenticationFilter(UsersService userService,Environment env) {
		this.userService=userService;
		this.env=env;
	}
	//attemptAuthentication method is being called by spring framework. It returns Authentication instance.
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,HttpServletResponse res) throws AuthenticationException{
		try {
			//User login request data
			LoginRequestModel creds=new ObjectMapper().readValue(req.getInputStream(),LoginRequestModel.class);
			//getAuthenticationManager() , method that uses same authentication manager that 
			return getAuthenticationManager().authenticate(
						new UsernamePasswordAuthenticationToken(creds.getEmail(),creds.getPassword(),new ArrayList<>())
					);
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	//successfulAuthentication method is being called after successful authentication.
	@Override
	protected void successfulAuthentication(HttpServletRequest req,HttpServletResponse res,
											FilterChain chain,
											Authentication auth) throws IOException,ServletException{
		//Here auth is an instance of UsernamePasswordAuthenticationToken.
		String username=((User)auth.getPrincipal()).getUsername();
		UserDto userDetails = userService.findByEmail(username);
		
		//io.jsonwebtoken is used to build JWT
		String token=Jwts.builder()
						.setSubject(userDetails.getUserId())
						.setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(env.getProperty("token.expiration.timeInMillis"))))
						.signWith(SignatureAlgorithm.HS512,env.getProperty("token.secret"))
						.compact();
		
		//setting token information to response header.
		res.addHeader("token",token);
		res.addHeader("userId", userDetails.getUserId());
		
	}

}
