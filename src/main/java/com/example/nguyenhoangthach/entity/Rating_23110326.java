package com.example.nguyenhoangthach.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating_23110326 {
    @EmbeddedId
    private RatingId_23110326 id;

    private Byte rating;

    @Column(columnDefinition = "TEXT")
    private String review_text;

    @ManyToOne
    @MapsId("userid")
    @JoinColumn(name = "userid")
    private Users_23110326 user;

    @ManyToOne
    @MapsId("bookid")
    @JoinColumn(name = "bookid")
    private Books_23110326 book;
}
