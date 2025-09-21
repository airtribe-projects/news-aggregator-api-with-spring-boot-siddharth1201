package com.siddharth.newsaggregator.entity;

import lombok.Data;

@Data // A handy Lombok annotation for getters, setters, toString, etc.
public class PreferenceResponseDTO {
    private Long id;
    private String category;
    private String source;
    private String language;
    private String country;
    private Long userId; // Just include the user's ID, not the whole object.
}