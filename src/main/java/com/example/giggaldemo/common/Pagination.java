package com.example.giggaldemo.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Pagination {

    long currentElements;
    long currentPage;
    long totalElements;
    long totalPages;

    @Builder
    public Pagination(long currentElements, long currentPage, long totalElements, long totalPages) {
        this.currentElements = currentElements;
        this.currentPage = currentPage;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
