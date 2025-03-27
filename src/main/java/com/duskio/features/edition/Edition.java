package com.duskio.features.edition;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.publisher.Publisher;
import com.duskio.features.work.Work;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

@Entity
@Indexed
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class Edition extends AuditableWithID {

    @FullTextField(projectable = Projectable.YES)
    @Column(nullable = false)
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

    @IndexedEmbedded
    @ManyToOne
    @JoinColumn(name = "work_id", nullable = false)
    @JsonBackReference
    private Work work;
    
    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @JsonBackReference
    private Publisher publisher;
}
