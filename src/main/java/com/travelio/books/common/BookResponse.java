package com.travelio.books.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookResponse {

    private String id;
    private String title;
    private String thumbnail;
    private List<String> authors;
    private RatingResponse rating;
    private Boolean isWishlisted;

}
