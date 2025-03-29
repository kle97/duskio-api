package com.duskio.features.profile;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.review.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString(onlyExplicitlyIncluded = true)
public class Profile extends AuditableWithID {

    @Column(nullable = false, unique = true)
    @ToString.Include
    private String userId;

    @Column(nullable = false, unique = true)
    @ToString.Include
    private String username;

    @Column(nullable = false)
    @ToString.Include
    private String displayName;

    private String biography;
    
    private String location;
    
    private LocalDate dateOfBirth;
    
    private String website;
    
    private String profilePicture;
    
    private String backgroundPicture;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();
}
