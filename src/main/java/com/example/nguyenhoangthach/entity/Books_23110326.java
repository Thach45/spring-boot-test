package com.example.nguyenhoangthach.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Books_23110326 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookid;

    @Column(unique = true)
    private Integer isbn;

    @Column(length = 200)
    private String title;

    @Column(length = 100)
    private String publisher;

    @Column(precision = 6, scale = 2)
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate publish_date;

    @Column(length = 255)
    private String cover_image;

    private Integer quantity;

    @ManyToMany
    @JoinTable(
        name = "book_author",
        joinColumns = @JoinColumn(name = "bookid"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author_23110326> authors;
}
