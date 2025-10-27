package com.example.smartroom.common.vo;

import org.springframework.data.domain.Page;

/**
 * PaginationInfo là một record dùng để chứa thông tin phân trang.
 *
 * @param currentPage Trang hiện tại.
 * @param totalItems Tổng số mục.
 * @param totalPages Tổng số trang.
 * @param pageSize Kích thước trang.
 * @param hasNext Có trang tiếp theo hay không.
 * @param hasPrev Có trang trước hay không.
 */
public record PaginationInfo (
    int currentPage,
    int totalItems,
    int totalPages,
    int pageSize,
    boolean hasNext,
    boolean hasPrev
) {
    public static PaginationInfo fromPage(Page<?> page) {
        return new PaginationInfo(
            page.getNumber(),
            (int) page.getTotalElements(),
            page.getTotalPages(),
            page.getSize(),
            page.hasNext(),
            page.hasPrevious()
        );
    }
}
