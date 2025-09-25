package com.example.nguyenhoangthach.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingId_23110326 implements Serializable {
    private Integer userid;
    private Integer bookid;
}
