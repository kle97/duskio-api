package com.duskio.features.subject;

import com.duskio.common.entity.AuditableWithID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class Subject extends AuditableWithID {

    @Column(nullable = false)
    private String subjectName;
}
