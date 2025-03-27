package com.duskio.features.work;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.author.Author;
import com.duskio.features.edition.Edition;
import com.duskio.features.rating.Rating;
import com.duskio.features.subject.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed
@AllArgsConstructor
@NoArgsConstructor @Getter @Setter
public class Work extends AuditableWithID {
    
    @Column(nullable = false)
    private String title;

    private String description;

    private String olKey;

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Edition> editions = new HashSet<>();

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL, orphanRemoval = true)
    @IndexedEmbedded
    private Set<Rating> ratings = new HashSet<>();

    @ManyToMany
    @IndexedEmbedded
    private Set<Subject> subjects = new HashSet<>();

    @ManyToMany
    @IndexedEmbedded
    private Set<Author> authors = new HashSet<>();
}
