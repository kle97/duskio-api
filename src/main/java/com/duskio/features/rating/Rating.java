package com.duskio.features.rating;

import com.duskio.common.entity.AuditableWithID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class Rating extends AuditableWithID {

    @Column(nullable = false)
    private Integer score;

    @Column(name = "work_id", nullable = false)
    private Long workId;
}
