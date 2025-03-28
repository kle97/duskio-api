package com.duskio.features.profile;

import com.duskio.common.entity.AuditableWithID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.time.LocalDate;

@Entity
@Indexed
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString(onlyExplicitlyIncluded = true)
public class Profile extends AuditableWithID {

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
}
