package com.duskio.features.work;

import com.duskio.common.entity.AuditableWithID;
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "work_id")
    private Set<Rating> ratings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "work_subject", joinColumns = @JoinColumn(name = "work_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Subject> subjects = new HashSet<>();
}
