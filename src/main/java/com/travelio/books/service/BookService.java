package com.travelio.books.service;

import com.travelio.books.client.OpenLibraryClient;
import com.travelio.books.common.*;
import com.travelio.books.entity.WishlistBook;
import com.travelio.books.repository.WishlistBookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private final WishlistBookRepository wishlistBookRepository;
    private final OpenLibraryClient openLibraryClient;
    private final ObjectMapper objectMapper;

    public List<BookResponse> getBooks(String keyword) {
        String json = openLibraryClient.getBooks();
        List<String> wishlistIds = wishlistBookRepository.findAll()
                .stream()
                .map(WishlistBook::getId)
                .collect(Collectors.toList());
        OpenLibraryResponse response = objectMapper.readValue(json, OpenLibraryResponse.class);

        return response.getReadingLongEntries().stream().filter(entry -> keyword == null
                || keyword.trim().isEmpty()
                || entry.getBookWorkResponse().getTitle()
                .toLowerCase()
                .contains(keyword.trim().toLowerCase()))
                .limit(50)
        .map(entry -> mapLogEntryToResponse(entry, wishlistIds)).collect(Collectors.toList());
    }

    private BookResponse mapLogEntryToResponse(ReadingLogEntryResponse readingLogEntryResponse, List<String> wishlistIds) {
        BookResponse response = new BookResponse();
        BookWorkResponse bookWorkResponse = readingLogEntryResponse.getBookWorkResponse();

        String key = bookWorkResponse.getKey().replace("/works/", "");
        response.setId(key);
        response.setAuthors(bookWorkResponse.getAuthorNames());
        response.setTitle(bookWorkResponse.getTitle());
        response.setThumbnail("/id/" + bookWorkResponse.getCoverId());
        response.setIsWishlisted(wishlistIds.contains(key));
        response.setRating(getRating(key).getSummary());
        return response;
    }

    private OpenLibraryRatingResponse getRating(String key) {
        String json = openLibraryClient.getRating(key);
        return objectMapper.readValue(json, OpenLibraryRatingResponse.class);
    }

    public List<BookResponse> getWishlistBooks() {
        List<String> wishlistIds = wishlistBookRepository.findAll()
                .stream()
                .map(WishlistBook::getId)
                .collect(Collectors.toList());
        return wishlistBookRepository.findAll().stream().map(entry -> mapEntityToResponse(entry, wishlistIds)).collect(Collectors.toList());
    }

    private BookResponse mapEntityToResponse(WishlistBook wishlistBook, List<String> wishlistIds) {
        BookResponse response = new BookResponse();
        response.setId(wishlistBook.getId());
        response.setAuthors(Arrays.asList(wishlistBook.getAuthors()));
        response.setTitle(wishlistBook.getTitle());
        response.setThumbnail(wishlistBook.getThumbnail());
        response.setRating(getRating(wishlistBook.getId()).getSummary());
        response.setIsWishlisted(wishlistBookRepository.existsById(wishlistBook.getId()));
        return response;
    }

    @Transactional
    public void addToWishlist(CreateWishlistBookRequest request) {
        WishlistBook wishlistBook = new WishlistBook();

        if (!wishlistBookRepository.existsById(request.getId())) {
            wishlistBook.setId(request.getId());
            wishlistBook.setTitle(request.getTitle());
            wishlistBook.setThumbnail(request.getThumbnail());
            wishlistBook.setAuthors(request.getAuthors().toArray(new String[0]));
            wishlistBookRepository.save(wishlistBook);
        }
    }

    @Transactional
    public void deleteFromWishlist(String id) {
        if (wishlistBookRepository.existsById(id)) {
            wishlistBookRepository.deleteById(id);
        }
    }
}
