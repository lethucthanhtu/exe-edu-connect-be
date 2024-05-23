package com.theeduconnect.exeeduconnectbe.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ListUtils {
    public static <T> Page<T> SetToPaginatedList(Set<T> itemsSet, Pageable pageable) {
        List<T> items = new ArrayList<T>(itemsSet);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), items.size());
        if (start > end) return Page.empty(pageable);
        ;
        List<T> pageContent = items.subList(start, end);
        return new PageImpl<>(pageContent, pageable, items.size());
    }
}
