package com.example.nguyenhoangthach.service;

import com.example.nguyenhoangthach.entity.Author_23110326;
import com.example.nguyenhoangthach.repository.AuthorRepository_23110326;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService_23110326 {

    @Autowired
    private AuthorRepository_23110326 authorRepository;

    public Page<Author_23110326> getAllAuthors(int page, int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return authorRepository.findAll(pageable);
    }

    public Page<Author_23110326> searchAuthors(String keyword, int page, int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return authorRepository.findByKeyword(keyword, pageable);
    }

    public Optional<Author_23110326> getAuthorById(Integer id) {
        return authorRepository.findById(id);
    }

    public Author_23110326 saveAuthor(Author_23110326 author) {
        return authorRepository.save(author);
    }

    public Author_23110326 updateAuthor(Author_23110326 author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(Integer id) {
        authorRepository.deleteById(id);
    }

    public List<Author_23110326> getAllAuthors() {
        return authorRepository.findAll();
    }
}
