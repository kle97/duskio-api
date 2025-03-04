package com.duskio.features.edition;

import com.duskio.common.entity.AuditableWithID;
import com.duskio.features.publisher.Publisher;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class Edition extends AuditableWithID {

    @Column(nullable = false)
    private String title;

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
    
    private String language;
    
    private String publishDate;
    
    private String publishCountry;
    
    private String publishPlace;
    
    private String cover;
    
    private String olKey;

    @Column(nullable = false)
    private Integer grade;
    
    private Long workId;
    
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @Fetch(FetchMode.JOIN)
    private Publisher publisher;
}
