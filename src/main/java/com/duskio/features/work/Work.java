package com.duskio.features.work;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.author.Author;
import com.duskio.features.edition.Edition;
import com.duskio.features.subject.Subject;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexingDependency;

import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed
@AllArgsConstructor
@NoArgsConstructor @Getter @Setter
@ToString(onlyExplicitlyIncluded = true)
public class Work extends AuditableWithID {

    @ToString.Include
    @Column(nullable = false)
    private String title;

    private String description;

    private String olKey;

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Edition> editions = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "work_subject",
               joinColumns = @JoinColumn(name = "work_id"),
               inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @IndexedEmbedded(includeDepth = 1)
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    private Set<Subject> subjects = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "author_work",
               joinColumns = @JoinColumn(name = "work_id"),
               inverseJoinColumns = @JoinColumn(name = "author_id"))
    @IndexedEmbedded(includeDepth = 1)
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    private Set<Author> authors = new HashSet<>();
}
