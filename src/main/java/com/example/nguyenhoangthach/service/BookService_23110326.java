package com.example.nguyenhoangthach.service;

import com.example.nguyenhoangthach.entity.Books_23110326;
import com.example.nguyenhoangthach.entity.Author_23110326;
import com.example.nguyenhoangthach.entity.Rating_23110326;
import com.example.nguyenhoangthach.repository.BookRepository_23110326;
import com.example.nguyenhoangthach.repository.AuthorRepository_23110326;
import com.example.nguyenhoangthach.repository.RatingRepository_23110326;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService_23110326 {

    @Autowired
    private BookRepository_23110326 bookRepository;

    @Autowired
    private AuthorRepository_23110326 authorRepository;

    @Autowired
    private RatingRepository_23110326 ratingRepository;

    public Page<Books_23110326> getAllBooks(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return bookRepository.findAllWithAuthors(pageable);
    }

    public Page<Books_23110326> searchBooks(String keyword, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return bookRepository.findByKeywordWithAuthors(keyword, pageable);
    }

    public Optional<Books_23110326> getBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public Books_23110326 saveBook(Books_23110326 book) {
        return bookRepository.save(book);
    }

    public Books_23110326 updateBook(Books_23110326 book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    public List<Books_23110326> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Books_23110326> getAllBooksWithAuthors() {
        return bookRepository.findAllWithAuthors();
    }

    public Optional<Books_23110326> findByIsbn(Integer isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Transactional
    public void assignAuthorsToBook(Books_23110326 book, List<Integer> authorIds) {
        Set<Author_23110326> authors = new HashSet<>();
        for (Integer authorId : authorIds) {
            Optional<Author_23110326> author = authorRepository.findById(authorId);
            if (author.isPresent()) {
                authors.add(author.get());
            }
        }
        book.setAuthors(authors);
    }

    public Double getAverageRating(Integer bookId) {
        List<Rating_23110326> ratings = ratingRepository.findByBookId(bookId);
        if (ratings.isEmpty()) {
            return 0.0;
        }
        
        double sum = ratings.stream()
                .mapToDouble(rating -> rating.getRating().doubleValue())
                .sum();
        
        return sum / ratings.size();
    }

    public Integer getRatingCount(Integer bookId) {
        return ratingRepository.findByBookId(bookId).size();
    }
}
