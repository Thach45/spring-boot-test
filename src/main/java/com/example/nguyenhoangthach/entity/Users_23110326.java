package com.example.nguyenhoangthach.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users_23110326 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(length = 50)
    private String fullname;

    private Integer phone;

    @Column(nullable = false, length = 32)
    private String passwd;

    private LocalDateTime signup_date;

    private LocalDateTime last_login;

    @Column(columnDefinition = "BIT DEFAULT 0")
    private Boolean is_admin;
}
