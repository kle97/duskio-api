package com.duskio.common;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ScrollResponse<T> {
    
    private final List<T> content;
    
    private final int size;

    private final int limit;
    
    private final boolean next;
    
    public ScrollResponse(List<T> content, int limit) {
        this.content = content;
        this.size = content.size();
        this.limit = limit;
        this.next = size >= limit;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
}
