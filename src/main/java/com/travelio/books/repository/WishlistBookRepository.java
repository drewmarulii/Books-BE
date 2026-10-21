package com.travelio.books.repository;

import com.travelio.books.entity.WishlistBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistBookRepository extends JpaRepository<WishlistBook, String> {
}
