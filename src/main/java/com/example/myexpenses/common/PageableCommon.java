package com.example.myexpenses.common;

import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableCommon {

    private PageableCommon() {}

    public static Pageable create(Integer page, Integer size, Sort.Direction sort,
            String property) {
        return Objects.nonNull(sort)
                ? PageRequest.of(page, size, Sort.by(sort, property))
                : PageRequest.of(page, size);
    }
}
