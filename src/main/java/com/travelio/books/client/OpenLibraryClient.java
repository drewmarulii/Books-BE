package com.travelio.books.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenLibraryClient {
    
    private final RestTemplate restTemplate = new RestTemplate();

    public String getBooks() {
        UriComponentsBuilder url = UriComponentsBuilder.fromUriString("https://openlibrary.org/people/mekBot/books/want-to-read.json");
        String finalUrl = url.encode().toUriString();
        return restTemplate.getForObject(finalUrl, String.class);
    }

    public String getRating(String key) {
        UriComponentsBuilder url = UriComponentsBuilder.fromUriString("https://openlibrary.org/works/" + key + "/ratings.json");
        String finalUrl = url.encode().toUriString();
        return restTemplate.getForObject(finalUrl, String.class);
    }

}
