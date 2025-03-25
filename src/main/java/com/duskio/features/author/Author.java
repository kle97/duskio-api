package com.duskio.features.author;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.alternatename.AlternateName;
import com.duskio.features.authorlink.AuthorLink;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class Author extends AuditableWithID {

    @FullTextField(projectable = Projectable.YES)
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
    private Set<AlternateName> alternateNames = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "authorId")
    private Set<AuthorLink> links = new HashSet<>();
}
