package com.duskio.features.subject;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.work.Work;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString(onlyExplicitlyIncluded = true)
public class Subject extends AuditableWithID {

    @GenericField(projectable = Projectable.YES, aggregable = Aggregable.YES)
    @Column(nullable = false)
    @ToString.Include
    private String subjectName;

    @ManyToMany(mappedBy = "subjects")
    private Set<Work> works = new HashSet<>();
}
