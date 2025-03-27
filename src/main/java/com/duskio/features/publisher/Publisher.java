package com.duskio.features.publisher;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.edition.Edition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class Publisher extends AuditableWithID {

    @GenericField(aggregable = Aggregable.YES)
    @Column(nullable = false)
    private String publisherName;

    @OneToMany(mappedBy = "publisher")
    private Set<Edition> editions = new HashSet<>();
}
