package com.duskio.features.work;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.rating.Rating;
import com.duskio.features.worksubject.WorkSubject;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter @ToString
public class Work extends AuditableWithID {

    @Column(nullable = false)
    private String title;

    private String olKey;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "workId")
    private Set<Rating> ratings = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "subjectId")
    private Set<WorkSubject> subjects = new HashSet<>();
}
