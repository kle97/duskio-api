package com.duskio.features.author;

import com.duskio.common.entity.AuditableWithID;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class AuthorLink extends AuditableWithID {

    private String title;

    private String url;

    private Long authorId;
}
