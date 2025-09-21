package com.siddharth.newsaggregator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siddharth.newsaggregator.entity.Preferences;
import com.siddharth.newsaggregator.entity.User;
import com.siddharth.newsaggregator.repository.PreferenceRepository;

@Service
public class PreferenceService {
    
    @Autowired
    PreferenceRepository preferenceRepository;

    @Autowired
    UserService userService;

    public Preferences savePreference(Preferences preference, Long userId) {
        User user = userService.getUserById(userId);
        preference.setUser(user);
        return preferenceRepository.save(preference);
    }

    public List<Preferences> getAllPreferencesByUser(Long userId) {
        User user = userService.getUserById(userId);
        return preferenceRepository.findAllByUser(user);
    }
    
    public Preferences updatePreference(Integer preferenceId, Preferences preferenceDetails, Long userId) {
        // First verify the preference exists and belongs to the user
        Preferences existingPreference = preferenceRepository.findById(preferenceId)
            .orElseThrow(() -> new RuntimeException("Preference not found with id: " + preferenceId));
            
        if (!existingPreference.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to update this preference");
        }
        
        // Update the fields from preferenceDetails
        if (preferenceDetails.getCategory() != null) {
            existingPreference.setCategory(preferenceDetails.getCategory());
        }
        if (preferenceDetails.getSource() != null) {
            existingPreference.setSource(preferenceDetails.getSource());
        }
        if (preferenceDetails.getLanguage() != null) {
            existingPreference.setLanguage(preferenceDetails.getLanguage());
        }
        if (preferenceDetails.getCountry() != null) {
            existingPreference.setCountry(preferenceDetails.getCountry());
        }
        
        return preferenceRepository.save(existingPreference);
    }
}
