package com.example.nguyenhoangthach.repository;

import com.example.nguyenhoangthach.entity.Author_23110326;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthorRepository_23110326 extends JpaRepository<Author_23110326, Integer> {
    
    @Query("SELECT a FROM Author_23110326 a WHERE a.author_name LIKE %:keyword%")
    Page<Author_23110326> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
