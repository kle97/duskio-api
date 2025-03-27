package com.duskio.features.rating;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.work.Work;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@Indexed
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class Rating extends AuditableWithID {
    
    @GenericField(projectable = Projectable.YES)
    @Column(nullable = false)
    private Integer score;
    
    @ManyToOne
    @JoinColumn(name = "work_id", nullable = false)
    @JsonBackReference
    private Work work;
}
