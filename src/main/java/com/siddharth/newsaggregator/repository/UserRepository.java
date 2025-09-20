package com.siddharth.newsaggregator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.siddharth.newsaggregator.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    
    @Query("SELECT u FROM User u JOIN FETCH u.preferences WHERE u.username = :username")
    Optional<User> findByUsernameWithPreferences(@Param("username") String username);
    
}
