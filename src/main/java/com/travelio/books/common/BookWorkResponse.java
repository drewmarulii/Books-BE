package com.travelio.books.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class BookWorkResponse {

    private String title;
    private String key;

    @JsonProperty("author_names")
    private List<String> authorNames;

    @JsonProperty("cover_id")
    private String coverId;

}
