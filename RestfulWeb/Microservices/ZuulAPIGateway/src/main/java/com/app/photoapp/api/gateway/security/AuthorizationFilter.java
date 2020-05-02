package com.app.photoapp.api.gateway.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter{
	Environment env;
	
	public AuthorizationFilter(AuthenticationManager authManager,Environment env) {
		super(authManager);
		this.env=env;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest req,
			HttpServletResponse res,
			FilterChain chain) throws IOException,ServletException{
		String authorization=req.getHeader(env.getProperty("authorization.token.header.name"));
//		//for testing purpose.
//		String test=env.getProperty("config.server.test.property");
//		System.out.println("------------------inside doFilterInternal() method--------------"+test);
		
		if(authorization==null || !authorization.startsWith(env.getProperty("authorization.token.header.prefix"))) {
			chain.doFilter(req, res);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication= getAuthentication(req); 
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
		String authorization=req.getHeader(env.getProperty("authorization.token.header.name"));
		if(authorization==null) {
			return null;
		}
		String token=authorization.replace(env.getProperty("authorization.token.header.prefix"), "");
		
		String userId=Jwts.parser()
						.setSigningKey(env.getProperty("token.secret"))
						.parseClaimsJws(token)
						.getBody()
						.getSubject();
		
		if(userId==null) {
			return null;
		}
		return new UsernamePasswordAuthenticationToken(userId,null,new ArrayList<>()) ;
	}

}
