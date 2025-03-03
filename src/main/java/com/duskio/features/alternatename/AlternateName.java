package com.duskio.features.alternatename;

import com.duskio.common.entity.AuditableWithID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "ALTERNATE_NAME")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class AlternateName extends AuditableWithID {

    @Column(nullable = false)
    private String alternateName;

    @Column(nullable = false)
    private Long authorId;
}
