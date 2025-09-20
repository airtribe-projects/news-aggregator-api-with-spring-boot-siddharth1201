package com.siddharth.newsaggregator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siddharth.newsaggregator.entity.User;
import com.siddharth.newsaggregator.entity.UserLoginDTO;
import com.siddharth.newsaggregator.service.UserService;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("signin")
    public String signin(@RequestBody UserLoginDTO userLoginDTO) {
        // check if user has provided username or email
        if(userLoginDTO.getUsernameOrEmail() == null || userLoginDTO.getPassword() == null) {
            return "Please provide username or email and password";
        }
        User user = null;
        if(userLoginDTO.getUsernameOrEmail().contains("@")){
            // check if email exists in database
            user = userService.getUserByEmail(userLoginDTO.getUsernameOrEmail());
        } else {
            // check if username exists in database
            user = userService.getUserByUsername(userLoginDTO.getUsernameOrEmail());
        }

        if(user == null) {
            return "User not found";
        }

        // check if hashed password is correct
        boolean isPasswordCorrect = passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword());
        if(!isPasswordCorrect) {
            return "Password is incorrect";
        }


        // return token

        return TokenUtil.generateToken(user);
    }

}
