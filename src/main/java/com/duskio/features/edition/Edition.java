package com.duskio.features.edition;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.publisher.Publisher;
import com.duskio.features.review.Review;
import com.duskio.features.work.Work;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString(onlyExplicitlyIncluded = true)
public class Edition extends AuditableWithID {

    @FullTextField(projectable = Projectable.YES)
    @Column(nullable = false)
    @ToString.Include
    private String title;

    @FullTextField(projectable = Projectable.YES)
    private String subtitle;

    private String description;

    private String pagination;

    private Integer numberOfPages;

    private String volumns;

    private String physicalFormat;
    
    private String physicalDimensions;
    
    private String weight;

    @Column(name = "isbn_10")
    private String isbn10;

    @Column(name = "isbn_13")
    private String isbn13;
    
    private String oclcNumber;
    
    private String lccnNumber;
    
    private String deweyNumber;
    
    private String lcClassifications;
    
    @GenericField(aggregable = Aggregable.YES)
    private String language;

    @GenericField(aggregable = Aggregable.YES)
    private String publishDate;

    @GenericField(aggregable = Aggregable.YES)
    private String publishCountry;

    @GenericField(aggregable = Aggregable.YES)
    private String publishPlace;
    
    @GenericField(projectable = Projectable.YES)
    private String cover;
    
    private String olKey;

    @Column(nullable = false)
    private Integer grade;

    @GenericField(projectable = Projectable.YES, sortable = Sortable.YES)
    private Double averageRating;

    @GenericField(projectable = Projectable.YES, sortable = Sortable.YES)
    private Integer ratingCount;

    @OneToMany(mappedBy = "edition", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    @IndexedEmbedded
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id")
    @JsonBackReference
    private Work work;
    
    @IndexedEmbedded
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @JsonBackReference
    private Publisher publisher;
}
