package com.example.smartroom.common.util;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import com.example.smartroom.common.vo.PaginationInfo;

/**
 * ApiResponse là một record dùng để chuẩn hóa cấu trúc phản hồi API.
 *
 * @param <T> Kiểu dữ liệu của trường data.
 * @param status Mã trạng thái HTTP của phản hồi.
 * @param message Thông điệp mô tả kết quả hoặc lỗi.
 * @param data Dữ liệu trả về từ API (nếu có).
 * @param paginationInfo Thông tin phân trang (nếu có).
 * @param timestamp Thời điểm phản hồi được tạo ra.
 */
public record ApiResponse<T>(int status, String message, T data, PaginationInfo paginationInfo, Instant timestamp) {

    /**
     * Tạo một ApiResponse thành công.
     * @param <T> Kiểu dữ liệu của trường data.
     * @param status Mã trạng thái HTTP của phản hồi.
     * @param message Thông điệp mô tả kết quả.
     * @param data Dữ liệu trả về từ API.
     * @return ApiResponse thành công.
     */
    public static <T> ApiResponse<T> success(HttpStatus status, String message, T data) {
        return new ApiResponse<>(status.value(), message, data, null, Instant.now());
    }
    
    /**
     * Tạo một ApiResponse thành công với dữ liệu phân trang.
     * @param <T> Kiểu dữ liệu của trường data.
     * @param status Mã trạng thái HTTP của phản hồi.
     * @param message Thông điệp mô tả kết quả.
     * @param pageData Dữ liệu phân trang trả về từ API.
     * @return ApiResponse thành công với dữ liệu phân trang.
     */
    public static <T> ApiResponse<List<T>> success(HttpStatus status, String message, Page<T> pageData) {
        PaginationInfo paginationInfo = PaginationInfo.fromPage(pageData);

        List<T> items = pageData.getContent();

        return new ApiResponse<>(status.value(), message, items, paginationInfo, Instant.now());
    }

    /**
     * Tạo một ApiResponse lỗi đơn giản chỉ với mã trạng thái và thông điệp lỗi.
     * @param <T> Kiểu dữ liệu của trường data.
     * @param status Mã trạng thái HTTP của phản hồi.
     * @param message Thông điệp mô tả lỗi.
     * @return ApiResponse lỗi.
     */
    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return new ApiResponse<>(status.value(), message, null, null, Instant.now());
    }

    /**
     * Tạo một ApiResponse lỗi với dữ liệu lỗi.
     * @param <T> Kiểu dữ liệu của trường data.
     * @param status Mã trạng thái HTTP của phản hồi.
     * @param message Thông điệp mô tả lỗi.
     * @param errorData Dữ liệu lỗi trả về từ API.
     * @return ApiResponse lỗi với dữ liệu lỗi.
     */
    public static <T> ApiResponse<T> error(HttpStatus status, String message, T errorData) {
        return new ApiResponse<>(status.value(), message, errorData, null, Instant.now());
    }
}