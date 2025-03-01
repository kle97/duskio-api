package com.duskio.features.author;

import com.duskio.common.entity.AuditableWithID;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter @ToString
public class Author extends AuditableWithID {

    @Column(nullable = false)
    private String authorName;

    private String birthDate;

    private String deathDate;

    private String authorDate;

    private String biography;

    private String photo;

    private String olKey;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "authorId")
    private Set<AuthorAlternateName> alternateNames = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "authorId")
    private Set<AuthorLink> links = new HashSet<>();
}
