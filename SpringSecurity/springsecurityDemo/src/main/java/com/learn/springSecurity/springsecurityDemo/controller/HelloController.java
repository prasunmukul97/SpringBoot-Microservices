package com.learn.springSecurity.springsecurityDemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home(){
        return ("<h1> Welcome </h1>");
    }

    @GetMapping("/user")
    public String getUser(){
        return ("<h1> Welcome User </h1>");
    }

    @GetMapping("/admin")
    public String getAdmin(){
        return ("<h1> Welcome Admin</h1>");
    }
}
