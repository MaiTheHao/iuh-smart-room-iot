package com.example.smartroom.core.common.util;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

import com.example.smartroom.core.common.annotation.Filterable;
import com.example.smartroom.core.common.annotation.Searchable;
import com.example.smartroom.core.common.annotation.Sortable;

/**
 * Tiện ích lấy thông tin metadata của entity trong hệ thống Smart Room.
 * Hỗ trợ xác định các trường có thể sắp xếp, lọc, tìm kiếm dựa trên annotation.
 * Metadata được cache để tối ưu hiệu năng.
 */
@Component
public class EntityMetadataUtil {

    /**
     * Cache lưu trữ metadata của các entity đã được phân tích.
     * Key là class của entity, value là EntityMetadata tương ứng.
     */
    private final Map<Class<?>, EntityMetadata> metadataCache = new ConcurrentHashMap<>();

    /**
     * Lấy metadata của entity dựa trên class.
     * Nếu chưa có trong cache, sẽ tự động phân tích và lưu lại.
     *
     * @param entityClass Class của entity cần lấy metadata.
     * @return EntityMetadata chứa thông tin các trường có thể sắp xếp, lọc, tìm kiếm.
     */
    public EntityMetadata getMetadata(Class<?> entityClass) {
        return metadataCache.computeIfAbsent(entityClass, this::buildMetadata);
    }

    /**
     * Phân tích class entity để xây dựng metadata về các trường có annotation Sortable, Filterable, Searchable.
     * Duyệt cả các lớp cha để tổng hợp đầy đủ thông tin.
     *
     * @param entityClass Class của entity cần phân tích.
     * @return EntityMetadata chứa danh sách trường có thể sắp xếp, lọc, tìm kiếm.
     */
    private EntityMetadata buildMetadata(Class<?> entityClass) {
        Set<String> sortableFields = new HashSet<>();
        Set<String> filterableFields = new HashSet<>();
        Set<String> searchableFields = new HashSet<>();

        Class<?> currentClass = entityClass;
        while (currentClass != null && currentClass != Object.class) {
            for (Field field : currentClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Sortable.class)) {
                    sortableFields.add(field.getName());
                }
                if (field.isAnnotationPresent(Filterable.class)) {
                    filterableFields.add(field.getName());
                }
                if (field.isAnnotationPresent(Searchable.class)) {
                    searchableFields.add(field.getName());
                }
            }
            currentClass = currentClass.getSuperclass();
        }

        return new EntityMetadata(sortableFields, filterableFields, searchableFields);
    }

    /**
     * Lớp đại diện cho metadata của một entity.
     * Lưu trữ danh sách các trường có thể sắp xếp, lọc, tìm kiếm.
     */
    public static class EntityMetadata {

        /**
         * Danh sách tên trường có thể sắp xếp.
         */
        private final Set<String> sortableFields;

        /**
         * Danh sách tên trường có thể lọc.
         */
        private final Set<String> filterableFields;

        /**
         * Danh sách tên trường có thể tìm kiếm.
         */
        private final Set<String> searchableFields;

        /**
         * Khởi tạo EntityMetadata với các trường đã phân tích.
         *
         * @param sortable   Danh sách trường có thể sắp xếp.
         * @param filterable Danh sách trường có thể lọc.
         * @param searchable Danh sách trường có thể tìm kiếm.
         */
        public EntityMetadata(Set<String> sortable, Set<String> filterable, Set<String> searchable) {
            this.sortableFields = Collections.unmodifiableSet(sortable);
            this.filterableFields = Collections.unmodifiableSet(filterable);
            this.searchableFields = Collections.unmodifiableSet(searchable);
        }

        /**
         * Kiểm tra trường có thể sắp xếp hay không.
         *
         * @param fieldName Tên trường cần kiểm tra.
         * @return true nếu trường có thể sắp xếp, ngược lại false.
         */
        public boolean isSortable(String fieldName) {
            return sortableFields.contains(fieldName);
        }

        /**
         * Kiểm tra trường có thể lọc hay không.
         *
         * @param fieldName Tên trường cần kiểm tra.
         * @return true nếu trường có thể lọc, ngược lại false.
         */
        public boolean isFilterable(String fieldName) {
            return filterableFields.contains(fieldName);
        }

        /**
         * Kiểm tra trường có thể tìm kiếm hay không.
         *
         * @param fieldName Tên trường cần kiểm tra.
         * @return true nếu trường có thể tìm kiếm, ngược lại false.
         */
        public boolean isSearchable(String fieldName) {
            return searchableFields.contains(fieldName);
        }

        /**
         * Lấy danh sách trường có thể sắp xếp.
         *
         * @return Set tên trường có thể sắp xếp.
         */
        public Set<String> getSortableFields() {
            return sortableFields;
        }

        /**
         * Lấy danh sách trường có thể lọc.
         *
         * @return Set tên trường có thể lọc.
         */
        public Set<String> getFilterableFields() {
            return filterableFields;
        }

        /**
         * Lấy danh sách trường có thể tìm kiếm.
         *
         * @return Set tên trường có thể tìm kiếm.
         */
        public Set<String> getSearchableFields() {
            return searchableFields;
        }
    }
}
