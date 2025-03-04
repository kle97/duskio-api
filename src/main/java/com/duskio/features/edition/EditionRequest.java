package com.duskio.features.edition;

public record EditionRequest(
        String title,
        String subtitle,
        String description,
        String pagination,
        Integer numberOfPages,
        String volumns,
        String physicalFormat,
        String physicalDimensions,
        String weight,
        String isbn10,
        String isbn13,
        String oclcNumber,
        String lccnNumber,
        String deweyNumber,
        String lcClassifications,
        String language,
        String publishDate,
        String publishCountry,
        String publishPlace,
        String cover,
        Long workId,
        Long publisherId
) {
}
