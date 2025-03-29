package com.duskio.features.review;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.edition.Edition;
import com.duskio.features.profile.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@Indexed
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString(onlyExplicitlyIncluded = true)
public class Review extends AuditableWithID {

    @EmbeddedId
    private ReviewId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("profileId")
    @JoinColumn(name = "profile_id")
    Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("editionId")
    @JoinColumn(name = "edition_id")
    Edition edition;
    
    @GenericField(projectable = Projectable.YES)
    @Column(nullable = false)
    @ToString.Include
    private Integer score;

    @ToString.Include
    private String review;
}
