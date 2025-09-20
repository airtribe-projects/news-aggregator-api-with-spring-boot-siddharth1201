package com.siddharth.newsaggregator.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siddharth.newsaggregator.entity.PreferenceRequestDTO;
import com.siddharth.newsaggregator.entity.PreferenceResponseDTO;
import com.siddharth.newsaggregator.entity.Preferences;
import com.siddharth.newsaggregator.service.PreferenceService;
import com.siddharth.newsaggregator.service.UserService;

import org.springframework.security.core.Authentication; 


@RestController
@RequestMapping("/api/preference/")
public class PreferenceController {

    @Autowired
    PreferenceService preferenceService;

    @Autowired
    UserService userService;

    private PreferenceResponseDTO mapToPreferenceResponseDTO(Preferences preference) {
        PreferenceResponseDTO dto = new PreferenceResponseDTO();
        dto.setId(preference.getId());
        dto.setCategory(preference.getCategory());
        dto.setSource(preference.getSource());
        dto.setLanguage(preference.getLanguage());
        dto.setCountry(preference.getCountry());
        dto.setUserId(preference.getUser().getId()); // Set only the ID
        return dto;
    }

    @PostMapping("")
    public ResponseEntity<PreferenceResponseDTO> savePreference(@RequestBody PreferenceRequestDTO preferenceRequestDTO, Authentication authentication) {
        // user builder to build pereference object
        String username = authentication.getName();
        Long userId = userService.getUserByUsername(username).getId();
        System.out.println("inside preference controller");
        Preferences preference = Preferences.builder().
            category(preferenceRequestDTO.getCategory()).
            source(preferenceRequestDTO.getSource()).
            language(preferenceRequestDTO.getLanguage()).
            country(preferenceRequestDTO.getCountry()).build();

        Preferences savedPreference = preferenceService.savePreference(preference, userId);
        return  ResponseEntity.ok().body(mapToPreferenceResponseDTO(savedPreference)); 
    }



    @GetMapping("")
    public ResponseEntity<List<PreferenceResponseDTO>> getAllPreferencesByUser(Authentication authentication) {
        String username = authentication.getName();
        Long userId = userService.getUserByUsername(username).getId();
        List<Preferences> preferences = preferenceService.getAllPreferencesByUser(userId);
        List<PreferenceResponseDTO> preferenceDTOs = preferences.stream()
            .map(this::mapToPreferenceResponseDTO)
            .toList();
        return ResponseEntity.ok().body(preferenceDTOs);
    }
    
    @PutMapping("/{preferenceId}")
    public ResponseEntity<PreferenceResponseDTO> updatePreference(
            @PathVariable("preferenceId") Integer preferenceId,
            @RequestBody PreferenceRequestDTO preferenceRequestDTO,
            Authentication authentication) {
        
        String username = authentication.getName();
        Long userId = userService.getUserByUsername(username).getId();
        
        // Convert DTO to entity
        Preferences preferenceDetails = Preferences.builder()
            .category(preferenceRequestDTO.getCategory())
            .source(preferenceRequestDTO.getSource())
            .language(preferenceRequestDTO.getLanguage())
            .country(preferenceRequestDTO.getCountry())
            .build();
            
        Preferences updatedPreference = preferenceService.updatePreference(
            preferenceId, preferenceDetails, userId);
            
        return ResponseEntity.ok(mapToPreferenceResponseDTO(updatedPreference));
    }
    
}
