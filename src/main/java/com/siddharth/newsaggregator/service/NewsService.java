package com.siddharth.newsaggregator.service;

import com.siddharth.newsaggregator.entity.Preferences;
import com.siddharth.newsaggregator.entity.User;
import com.siddharth.newsaggregator.repository.UserRepository;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private final WebClient.Builder webClientBuilder;
    private final UserRepository userRepository;
    private final String apiKey;

    @Value("${newsapi.baseurl}")
    private String newsApiBaseUrl;

    @Autowired
    public NewsService(WebClient.Builder webClientBuilder, UserRepository userRepository, @Value("${newsapi.apikey}") String apiKey) {
        this.webClientBuilder = webClientBuilder;
        this.userRepository = userRepository;
        this.apiKey = apiKey;
    }

    public Mono<String>  getNewsForUserPreferences(User user) {
        // 1. Fetch the user and their preferences
        // User user = userRepository.findById(userId)
        //         .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        List<Preferences> preferences = user.getPreferences();

        if (preferences.isEmpty()) {
            // You could return a default news feed or a specific message
            return Mono.just("{\"message\": \"User has no preferences set. Please add preferences to get a personalized news feed.\"}");
        }

        // 2. Aggregate preferences into query parameters
        // Using Sets to automatically handle duplicates
        Set<String> categories = preferences.stream()
                .map(Preferences::getCategory)
                .collect(Collectors.toSet());

        Set<String> sources = preferences.stream()
                .map(Preferences::getSource)
                .collect(Collectors.toSet());
        
        // The API takes one language. We'll pick the first one found.
        String language = preferences.stream()
                .map(Preferences::getLanguage)
                .findFirst()
                .orElse("en"); // Default to 'en' if none are set

        // 3. Build the query for the '/everything' endpoint
        // 'q' will be a combination of categories, e.g., "technology OR business OR sports"
        String query = String.join(" OR ", categories);
        String sourcesString = String.join(",", sources);

        // 4. Build the URI safely with UriComponentsBuilder
        URI uri = UriComponentsBuilder.fromPath("/everything")
                .queryParam("apiKey", this.apiKey)
                .queryParam("q", query)
                // .queryParam("sources", sourcesString)
                .queryParam("language", language)
                .queryParam("sortBy", "popularity") // You can make this configurable too
                .build()
                .toUri();

        // 5. Make the asynchronous call using WebClient
        WebClient webClient = webClientBuilder.build();
        System.out.println("URI: " + newsApiBaseUrl + uri.toString());
        return webClient.get()
                .uri(newsApiBaseUrl + uri)
                .retrieve()
                .bodyToMono(String.class) ; // .block() waits for the response. In a fully reactive app, you'd return the Mono.
    }
}