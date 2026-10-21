package com.travelio.books.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "wishlist_book")
@AllArgsConstructor
@NoArgsConstructor
public class WishlistBook {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Column(name = "authors", columnDefinition = "text[]")
    private String[] authors;

}
