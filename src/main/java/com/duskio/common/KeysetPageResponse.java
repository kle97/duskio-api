package com.duskio.common;

import lombok.Getter;

import java.util.List;

@Getter
public class KeysetPageResponse<T> {
    
    private final List<T> content;
    
    private final int size;

    private final int limit;
    
    private final boolean next;
    
    public KeysetPageResponse(List<T> content, int limit) {
        this.content = content;
        this.size = content.size();
        this.limit = limit;
        this.next = size <= limit;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
}
