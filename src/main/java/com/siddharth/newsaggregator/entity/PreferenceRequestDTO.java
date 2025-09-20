package com.siddharth.newsaggregator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreferenceRequestDTO {
 
    private String category;  // e.g., "technology"
    private String source;    // e.g., "bbc-news"
    private String language;  // e.g., "en"
    private String country;   // e.g., "us"
}
