package com.duskio.features.author;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.common.search.CustomAnalyzer;
import com.duskio.features.alternatename.AlternateName;
import com.duskio.features.authorlink.AuthorLink;
import com.duskio.features.work.Work;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString(onlyExplicitlyIncluded = true)
public class Author extends AuditableWithID {

    @FullTextField(analyzer = CustomAnalyzer.ENGLISH_ANALYZER, name = "authorName_fulltext")
    @GenericField(projectable = Projectable.YES, aggregable = Aggregable.YES)
    @Column(nullable = false)
    @ToString.Include
    private String authorName;

    private String birthDate;

    private String deathDate;

    private String authorDate;

    private String biography;

    private String photo;

    private String olKey;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AlternateName> alternateNames = new HashSet<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AuthorLink> links = new HashSet<>();
    
    @ManyToMany(mappedBy = "authors")
    private Set<Work> works = new HashSet<>();
}
