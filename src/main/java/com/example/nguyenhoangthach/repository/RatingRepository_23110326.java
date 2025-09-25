package com.example.nguyenhoangthach.repository;

import com.example.nguyenhoangthach.entity.Rating_23110326;
import com.example.nguyenhoangthach.entity.RatingId_23110326;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository_23110326 extends JpaRepository<Rating_23110326, RatingId_23110326> {
    
    @Query("SELECT r FROM Rating_23110326 r WHERE r.book.bookid = :bookId ORDER BY r.id.userid")
    List<Rating_23110326> findByBookId(@Param("bookId") Integer bookId);
    
    @Query("SELECT r FROM Rating_23110326 r WHERE r.user.id = :userId AND r.book.bookid = :bookId")
    Rating_23110326 findByUserIdAndBookId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);
}
