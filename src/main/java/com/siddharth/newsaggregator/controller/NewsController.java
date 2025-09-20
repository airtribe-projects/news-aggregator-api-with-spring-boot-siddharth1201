package com.siddharth.newsaggregator.controller;

import com.siddharth.newsaggregator.entity.User;
import com.siddharth.newsaggregator.service.NewsService;
import com.siddharth.newsaggregator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Mono<ResponseEntity<String>> getMyPersonalizedNews() {
        // Get the authentication object from the reactive context
        return ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication)
            .flatMap(authentication -> {
                String username = authentication.getName();

                // Wrap the blocking call and run it on a dedicated thread pool
                Mono<User> userMono = Mono.fromCallable(() -> userService.getUserByUsernameWithPreferences(username))
                                        .subscribeOn(Schedulers.boundedElastic());

                return userMono.flatMap(user -> newsService.getNewsForUserPreferences(user));
            })
            .map(jsonString -> ResponseEntity.ok(jsonString)); 
    }
}