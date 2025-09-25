package com.example.nguyenhoangthach.service;

import com.example.nguyenhoangthach.entity.Rating_23110326;
import com.example.nguyenhoangthach.entity.RatingId_23110326;
import com.example.nguyenhoangthach.entity.Books_23110326;
import com.example.nguyenhoangthach.entity.Users_23110326;
import com.example.nguyenhoangthach.repository.RatingRepository_23110326;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService_23110326 {

    @Autowired
    private RatingRepository_23110326 ratingRepository;

    public List<Rating_23110326> getRatingsByBookId(Integer bookId) {
        return ratingRepository.findByBookId(bookId);
    }

    public Rating_23110326 saveRating(Rating_23110326 rating) {
        return ratingRepository.save(rating);
    }

    public Rating_23110326 updateRating(Rating_23110326 rating) {
        return ratingRepository.save(rating);
    }

    public Optional<Rating_23110326> getRatingByUserAndBook(Integer userId, Integer bookId) {
        return Optional.ofNullable(ratingRepository.findByUserIdAndBookId(userId, bookId));
    }

    public void deleteRating(RatingId_23110326 ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    public Rating_23110326 createRating(Users_23110326 user, Books_23110326 book, Byte rating, String reviewText) {
        RatingId_23110326 ratingId = new RatingId_23110326(user.getId(), book.getBookid());
        Rating_23110326 ratingEntity = new Rating_23110326();
        ratingEntity.setId(ratingId);
        ratingEntity.setUser(user);
        ratingEntity.setBook(book);
        ratingEntity.setRating(rating);
        ratingEntity.setReview_text(reviewText);
        return ratingEntity;
    }
}
