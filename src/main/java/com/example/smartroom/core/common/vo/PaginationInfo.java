package com.example.smartroom.core.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * PaginationInfo là một DTO dùng để chứa thông tin phân trang và dữ liệu trang hiện tại.
 *
 * @param <T> Kiểu dữ liệu của nội dung (content).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationInfo<T> {
    private List<T> items;
    private int currentPage;
    private int pageSize;
    private long totalItems;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrev;

    /**
     * Tạo PaginationInfo từ một Page<E> (Entity) và một List<D> (DTO).
     *
     * @param entityPage Đối tượng Page<E> từ repository.
     * @param items      Danh sách items.
     * @param <E>        Kiểu Entity.
     * @param <D>        Kiểu DTO.
     * @return một đối tượng PaginationInfo<D>.
     */
    public static <E, D> PaginationInfo<D> from(Page<E> entityPage, List<D> items) {
        return new PaginationInfo<>(
            items,
            entityPage.getNumber(),
            entityPage.getSize(),
            entityPage.getTotalElements(),
            entityPage.getTotalPages(),
            entityPage.hasNext(),
            entityPage.hasPrevious()
        );
    }

    /**
     * Tạo PaginationInfo chỉ với thông tin phân trang, không có content.
     *
     * @param page Đối tượng Page<?> từ repository.
     * @return một đối tượng PaginationInfo<?>.
     */
    public static PaginationInfo<?> fromPage(Page<?> page) {
        return new PaginationInfo<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.hasNext(),
            page.hasPrevious()
        );
    }
}
