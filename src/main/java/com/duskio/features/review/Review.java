package com.duskio.features.review;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.edition.Edition;
import com.duskio.features.profile.Profile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString(onlyExplicitlyIncluded = true)
public class Review extends AuditableWithID {
    
    @Column(name = "profile_id", insertable = false, updatable = false)
    private Long profileId;

    @Column(name = "profile_id", insertable = false, updatable = false)
    private Long editionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    @JsonBackReference
    Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edition_id")
    @JsonBackReference
    Edition edition;
    
    @GenericField(projectable = Projectable.YES)
    @Column(nullable = false)
    @ToString.Include
    private Integer score;

    @ToString.Include
    private String review;
}
