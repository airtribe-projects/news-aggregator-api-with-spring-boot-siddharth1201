package com.siddharth.newsaggregator.service;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.siddharth.newsaggregator.entity.User;
import com.siddharth.newsaggregator.entity.UserDTO;
import com.siddharth.newsaggregator.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User registerUser(UserDTO userDTO) {
        // Here you can add additional logic like checking for existing users, hashing passwords, etc.
        // use builder to create user
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .role("ROLE_USER")
                .build();

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
  
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
  
        // return a custom UserDetails object
            User u = user.get();
            return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword())
                .authorities(u.getRole()) // or fetch roles from User if available
                .build();
  
    }

    public User getUserByEmail(String usernameOrEmail) {
        // TODO Auto-generated method stub
        Optional<User> user = userRepository.findByUsername(usernameOrEmail);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return user.get();
    }

    public User getUserByUsername(String usernameOrEmail) {
        // TODO Auto-generated method stub
       Optional<User> user = userRepository.findByUsername(usernameOrEmail);    
       if(user.isEmpty()) {
           throw new UsernameNotFoundException("User not found");
       }
       return user.get();
    }

    public User getUserByUsernameWithPreferences(String username) {
       // Call the new, efficient repository method
       return userRepository.findByUsernameWithPreferences(username)
           .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public User getUserById(Long userId) {
        // TODO Auto-generated method stub
        return userRepository.findById(userId).get();
    }


}
