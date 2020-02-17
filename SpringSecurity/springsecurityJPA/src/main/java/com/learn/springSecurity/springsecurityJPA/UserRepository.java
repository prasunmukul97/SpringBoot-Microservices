package com.learn.springSecurity.springsecurityJPA;

import com.learn.springSecurity.springsecurityJPA.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUserName(String username);
}
