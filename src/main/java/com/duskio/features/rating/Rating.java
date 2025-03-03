package com.duskio.features.rating;

import com.duskio.common.entity.AuditableWithID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter @ToString
public class Rating extends AuditableWithID {

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private Long workId;
}
