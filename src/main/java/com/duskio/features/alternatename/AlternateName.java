package com.duskio.features.alternatename;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.author.Author;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alternate_name")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class AlternateName extends AuditableWithID {

    @Column(nullable = false)
    private String alternateName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonBackReference
    private Author author;
}
