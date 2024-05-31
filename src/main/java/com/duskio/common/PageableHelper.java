package com.duskio.common;

import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

public class PageableHelper {
    
    private PageableHelper() {
    }
    
    public static String getQueryString(Pageable pageable) {
        return getOffset(pageable) + " " + getOrderBy(pageable) + " " + getLimit(pageable);
    }
    
    public static String getLimit(Pageable pageable) {
        return "fetch next " + pageable.getPageSize() + " rows only";
    }
    
    public static String getOffset(Pageable pageable) {
        return "offset " + pageable.getOffset() + " rows";
    }
    
    public static String getOrderBy(Pageable pageable) {
        return "order by " + pageable.getSort()
                       .stream()
                       .map(order -> order.getProperty() + " " + order.getDirection().name().toLowerCase())
                       .collect(Collectors.joining(", "));
    }
    
    
}
