package com.duskio.features.author;

import com.duskio.common.entity.AuditableWithID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "ALTERNATE_NAME")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class AuthorAlternateName extends AuditableWithID {

    private String alternateName;

    private Long authorId;
}
