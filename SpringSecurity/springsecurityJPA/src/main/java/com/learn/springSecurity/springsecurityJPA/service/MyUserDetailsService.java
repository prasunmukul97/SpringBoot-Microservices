package com.learn.springSecurity.springsecurityJPA.service;

import com.learn.springSecurity.springsecurityJPA.UserRepository;
import com.learn.springSecurity.springsecurityJPA.model.MyUserDetails;
import com.learn.springSecurity.springsecurityJPA.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> byUserName = userRepository.findByUserName(name);
        byUserName.orElseThrow(()->new UsernameNotFoundException("Not Found"+ name));
       //return new MyUserDetails(byUserName.get());
        return byUserName.map(MyUserDetails::new).get();
    }
}
