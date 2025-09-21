package com.siddharth.newsaggregator.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "preferences")
public class Preferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;  // e.g., "technology"
    private String source;    // e.g., "bbc-news"
    private String language;  // e.g., "en"
    private String country;   // e.g., "us"

    // Many preferences belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}