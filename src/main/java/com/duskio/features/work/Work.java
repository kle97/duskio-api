package com.duskio.features.work;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.author.Author;
import com.duskio.features.rating.Rating;
import com.duskio.features.subject.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor @Getter @Setter
public class Work extends AuditableWithID {

    @Column(nullable = false)
    private String title;

    private String olKey;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "work_id")
    private Set<Rating> ratings = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "work_subject", 
               joinColumns = @JoinColumn(name = "work_id"), 
               inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Subject> subjects = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "author_work",
               joinColumns = @JoinColumn(name = "work_id"),
               inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();
}
