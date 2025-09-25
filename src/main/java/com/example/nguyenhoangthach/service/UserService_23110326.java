package com.example.nguyenhoangthach.service;

import com.example.nguyenhoangthach.entity.Users_23110326;
import com.example.nguyenhoangthach.repository.UserRepository_23110326;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService_23110326 {

    @Autowired
    private UserRepository_23110326 userRepository_23110326;

    public Optional<Users_23110326> findByEmail(String email) {
        return userRepository_23110326.findByEmail(email);
    }

    public Users_23110326 saveUser(Users_23110326 user) {
        return userRepository_23110326.save(user);
    }

    public Users_23110326 getCurrentUser(String email) {
        return userRepository_23110326.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Users_23110326> getAllUsers() {
        return userRepository_23110326.findAll();
    }

    public void deleteUser(Integer id) {
        userRepository_23110326.deleteById(id);
    }

    public Optional<Users_23110326> getUserById(Integer id) {
        return userRepository_23110326.findById(id);
    }
}