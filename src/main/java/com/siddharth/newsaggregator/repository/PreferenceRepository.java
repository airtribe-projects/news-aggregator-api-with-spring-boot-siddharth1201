package com.siddharth.newsaggregator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siddharth.newsaggregator.entity.Preferences;
import com.siddharth.newsaggregator.entity.User;

public interface PreferenceRepository extends JpaRepository<Preferences, Integer> {


    List<Preferences> findAllByUser(User user);
    
}
