package com.duskio.features.category;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Category {

    @Id
    private Integer categoryId;

    private String name;
}
