package com.duskio.common;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PageableHelper {
    
    private PageableHelper() {
    }
    
    public static String getQueryString(Pageable pageable) {
        return getOrderBy(pageable) + " " + getOffset(pageable) + " " + getLimit(pageable);
    }
    
    public static String getLimit(Pageable pageable) {
        return "fetch next " + pageable.getPageSize() + " rows only";
    }
    
    public static String getOffset(Pageable pageable) {
        if (pageable.getOffset() == 0) {
            return "";
        }
        return "offset " + pageable.getOffset() + " rows";
    }
    
    public static String getOrderBy(Pageable pageable) {
        if (pageable.getSort().isEmpty()) {
            return "";
        }
        return "order by " + pageable.getSort()
                       .stream()
                       .map(order -> order.getProperty() + " " + order.getDirection().name().toLowerCase())
                       .collect(Collectors.joining(", "));
    }

    public static String getQueryString(ScrollRequest scrollRequest) {
        return getPosition(scrollRequest) + " order by " + getOrderBy(scrollRequest) + " fetch next " + scrollRequest.limit() + " rows only";
    }

    public static String getPosition(ScrollRequest scrollRequest) {
        if (scrollRequest.cursor() == null || scrollRequest.cursor().isEmpty()) {
            return "";
        }

        String orderBy = getOrderBy(scrollRequest);
        String properties = getOrderProperties(scrollRequest);
        String operator;
        if (!orderBy.contains("desc")) {
            if (scrollRequest.direction().equals(ScrollPosition.Direction.FORWARD)) {
                operator = " > ";
            } else {
                operator = " < ";
            }
        } else {
            if (scrollRequest.direction().equals(ScrollPosition.Direction.FORWARD)) {
                operator = " < ";
            } else {
                operator = " > ";
            }
        }

        StringBuilder sb = new StringBuilder(" where " + properties + operator + "(");
        for (int i = 0; i < scrollRequest.cursor().size(); i++) {
            String value = "'" + scrollRequest.cursor().get(i) + "'";
            if (i == 0) {
                sb.append(value);
            } else {
                sb.append(", ").append(value);
            }
        }
        sb.append(") ");

        return sb.toString();
    }

    public static String getOrderBy(ScrollRequest scrollRequest) {
        return toOrders(scrollRequest).stream()
                         .map(order -> order.getProperty() + " " + order.getDirection().name().toLowerCase())
                         .collect(Collectors.joining(", "));
    }

    public static List<Sort.Order> toOrders(ScrollRequest scrollRequest) {
        List<Sort.Order> orders = new ArrayList<>();
        String previousProperty = "";
        for (String s : scrollRequest.sort()) {
            s = s.toLowerCase();
            if (!s.equals("asc") && !s.equals("desc")) {
                if (!previousProperty.isEmpty()) {
                    orders.add(new Sort.Order(Sort.Direction.ASC, previousProperty));
                }
                previousProperty = s;
            } else {
                orders.add(new Sort.Order(Sort.Direction.fromString(s), previousProperty));
                previousProperty = "";
            }
        }
        if (!previousProperty.isEmpty()) {
            orders.add(new Sort.Order(Sort.Direction.ASC, previousProperty));
        }
        return orders;
    }

    public static String getOrderProperties(ScrollRequest scrollRequest) {
        return "(" + toOrders(scrollRequest).stream().map(Sort.Order::getProperty).collect(Collectors.joining(", ")) + ")";
    }
    
    
}
