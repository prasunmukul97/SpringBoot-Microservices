package com.app.photoapp.api.users.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.app.photoapp.api.users.service.UsersService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends  WebSecurityConfigurerAdapter{
	
	Environment env;
	UsersService usersService;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public WebSecurity(Environment env,UsersService usersService,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.env=env;
		this.usersService=usersService;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/users/**").permitAll()
		.and()
		.addFilter(getAuthenticationFilter());
		http.headers().frameOptions().disable();
	}

	private Filter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authFilter=new AuthenticationFilter(usersService,env);
		authFilter.setAuthenticationManager(authenticationManager()); //setting AuthenticationManager to Authentication Filter.
		authFilter.setFilterProcessesUrl(env.getProperty("login.url")); //It is used to provide custom login URL.
		return authFilter;
	}
	//Below method is used to build AuthenticationManager using AuthenticationManagerBuilder.
	//During authentication manager builder we set user details service ("UserDetailsService") and password encoder instances.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
		
	}

}
