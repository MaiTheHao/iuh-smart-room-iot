package com.example.smartroom.core.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * ApiResponse là một DTO dùng để chuẩn hóa cấu trúc phản hồi API.
 *
 * @param <T> Kiểu dữ liệu của trường data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseBody<T> {
    private int status;
    private String message;
    private T data;
    private Instant timestamp;

    // Success responses
    public static <T> ApiResponseBody<T> success(String message) {
        return ApiResponseBody.<T>builder()
            .status(HttpStatus.OK.value())
            .message("Success")
            .data(null)
            .timestamp(Instant.now())
            .build();
    }

    public static <T> ApiResponseBody<T> success(String message, T data) {
        return ApiResponseBody.<T>builder()
            .status(HttpStatus.OK.value())
            .message(message)
            .data(data)
            .timestamp(Instant.now())
            .build();
    }

    public static <T> ApiResponseBody<T> success(HttpStatus status, String message, T data) {
        return ApiResponseBody.<T>builder()
            .status(status.value())
            .message(message)
            .data(data)
            .timestamp(Instant.now())
            .build();
    }

    // Error responses
    public static <T> ApiResponseBody<T> error(HttpStatus status, String message) {
        return ApiResponseBody.<T>builder()
            .status(status.value())
            .message(message)
            .timestamp(Instant.now())
            .build();
    }

    public static <T> ApiResponseBody<T> error(HttpStatus status, String message, T errorData) {
        return ApiResponseBody.<T>builder()
            .status(status.value())
            .message(message)
            .data(errorData)
            .timestamp(Instant.now())
            .build();
    }

    public static <T> ApiResponseBody<T> notFound(String message) {
        return error(HttpStatus.NOT_FOUND, message);
    }

    public static <T> ApiResponseBody<T> badRequest(String message) {
        return error(HttpStatus.BAD_REQUEST, message);
    }

    public static <T> ApiResponseBody<T> internalError(String message) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}