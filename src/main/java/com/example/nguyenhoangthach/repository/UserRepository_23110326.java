package com.example.nguyenhoangthach.repository;

import com.example.nguyenhoangthach.entity.Users_23110326;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository_23110326 extends JpaRepository<Users_23110326, Integer> {
    Optional<Users_23110326> findByEmail(String email);
}
