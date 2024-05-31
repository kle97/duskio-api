package com.duskio.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public record KeysetPageRequest(List<Object> cursor, ScrollPosition.Direction direction, int limit, List<String> sort) {
    
    public String getQueryString() {
        return getPosition() + " order by " + getOrderBy() + " fetch next " + limit + " rows only";
    }
    
    public String getPosition() {
        if (cursor == null || cursor.isEmpty()) {
            return "";
        }
        
        String orderBy = getOrderBy();
        String properties = getOrderProperties();
        String operator;
        if (!orderBy.contains("desc")) {
            if (direction.equals(ScrollPosition.Direction.FORWARD)) {
                operator = " > ";
            } else {
                operator = " < ";
            }
        } else {
            if (direction.equals(ScrollPosition.Direction.FORWARD)) {
                operator = " < ";
            } else {
                operator = " > ";
            }
        }
        
        StringBuilder sb = new StringBuilder(" where " + properties + operator + "(");
        for (int i = 0; i < cursor.size(); i++) {
            String value = "'" + cursor.get(i) + "'"; 
            if (i == 0) {
                sb.append(value);
            } else {
                sb.append(", ").append(value);
            }
        }
        sb.append(") ");
        
        return sb.toString();
    }
    
    public String getOrderBy() {
        return toOrders().stream()
                         .map(order -> order.getProperty() + " " + order.getDirection().name().toLowerCase())
                         .collect(Collectors.joining(", "));
    }
    
    private List<Sort.Order> toOrders() {
        List<Sort.Order> orders = new ArrayList<>();
        String previousProperty = "";
        for (String s : sort) {
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
    
    private String getOrderProperties() {
        return "(" + toOrders().stream().map(Sort.Order::getProperty).collect(Collectors.joining(", ")) + ")";
    }
    
}
