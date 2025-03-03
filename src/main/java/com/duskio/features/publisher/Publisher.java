package com.duskio.features.publisher;

import com.duskio.common.entity.AuditableWithID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter @ToString
public class Publisher extends AuditableWithID {

    @Column(nullable = false)
    private String publisherName;
}
