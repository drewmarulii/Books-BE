package com.travelio.books.controller;

import com.travelio.books.common.BookResponse;
import com.travelio.books.common.CreateWishlistBookRequest;
import com.travelio.books.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookResponse>> getBooks(@RequestParam(name = "q", required = false) String keyword) {
        return ResponseEntity.ok(bookService.getBooks(keyword));
    }

    @GetMapping(value = "/wishlist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookResponse>> getWishlistBooks() {
        return ResponseEntity.ok(bookService.getWishlistBooks());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addToWishlist(@RequestBody CreateWishlistBookRequest request) {
        bookService.addToWishlist(request);
        return ResponseEntity.ok("Book has been added to Wishlist!");
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteFromWishlist(@PathVariable String id) {
        bookService.deleteFromWishlist(id);
        return ResponseEntity.ok("Book has been deleted from Wishlist!");
    }

}
