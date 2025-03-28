package com.duskio.features.publisher;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.edition.Edition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString(onlyExplicitlyIncluded = true)
public class Publisher extends AuditableWithID {

    @GenericField(aggregable = Aggregable.YES)
    @Column(nullable = false)
    @ToString.Include
    private String publisherName;

    @OneToMany(mappedBy = "publisher")
    private Set<Edition> editions = new HashSet<>();
}
