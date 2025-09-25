package com.example.nguyenhoangthach.repository;

import com.example.nguyenhoangthach.entity.Books_23110326;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository_23110326 extends JpaRepository<Books_23110326, Integer> {
    
    @Query("SELECT b FROM Books_23110326 b WHERE b.title LIKE %:keyword% OR b.publisher LIKE %:keyword%")
    Page<Books_23110326> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT DISTINCT b FROM Books_23110326 b LEFT JOIN FETCH b.authors")
    List<Books_23110326> findAllWithAuthors();
    
    @Query("SELECT DISTINCT b FROM Books_23110326 b LEFT JOIN FETCH b.authors")
    Page<Books_23110326> findAllWithAuthors(Pageable pageable);
    
    @Query("SELECT DISTINCT b FROM Books_23110326 b LEFT JOIN FETCH b.authors WHERE b.title LIKE %:keyword% OR b.publisher LIKE %:keyword%")
    Page<Books_23110326> findByKeywordWithAuthors(@Param("keyword") String keyword, Pageable pageable);
    
    Optional<Books_23110326> findByIsbn(Integer isbn);
}
