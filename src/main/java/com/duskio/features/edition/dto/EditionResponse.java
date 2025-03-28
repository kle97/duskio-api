package com.duskio.features.edition.dto;

import com.duskio.features.publisher.PublisherResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class EditionResponse extends EditionSimpleResponse {

    private String description;

    private String pagination;

    private Integer numberOfPages;

    private String volumns;

    private String physicalFormat;

    private String physicalDimensions;

    private String weight;

    private String isbn10;

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

    private Integer grade;

    private PublisherResponse publisher;
}
