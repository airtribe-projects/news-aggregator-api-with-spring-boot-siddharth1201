package com.siddharth.newsaggregator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siddharth.newsaggregator.entity.User;
import com.siddharth.newsaggregator.entity.UserDTO;
import com.siddharth.newsaggregator.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    
    @PostMapping("/register")
    public  ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO){
        // use builder to creat user

        User user = userService.registerUser(userDTO);
        System.out.println(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
}
