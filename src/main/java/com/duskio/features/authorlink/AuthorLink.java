package com.duskio.features.authorlink;

import com.duskio.common.entity.AuditableWithID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class AuthorLink extends AuditableWithID {

    private String title;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Long authorId;
}
