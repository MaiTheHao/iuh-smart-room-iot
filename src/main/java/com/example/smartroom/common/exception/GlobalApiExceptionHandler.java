package com.example.smartroom.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.smartroom.common.vo.ApiResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Xử lý các exception toàn cục cho ứng dụng. Trả về cấu trúc
 * {@link ApiResponse} thống nhất.
 */
@RestControllerAdvice(
	basePackages = { 
		"com.example.smartroom.device_management",
		"com.example.smartroom.data_ingestion" 
	}
)
@Slf4j
public class GlobalApiExceptionHandler {
	/**
	 * Xử lý NotFoundException.
	 *
	 * @param ex NotFoundException
	 * @return Phản hồi với {@link ApiResponse} chứa thông tin lỗi.
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> handleNotFound(NotFoundException ex) {
		ApiResponse<Object> apiResponse = ApiResponse.error(HttpStatus.NOT_FOUND, ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * Xử lý BadRequestException.
	 *
	 * @param ex BadRequestException
	 * @return Phản hồi với {@link ApiResponse} chứa thông tin lỗi.
	 */
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiResponse<Object>> handleBadRequest(BadRequestException ex) {
		ApiResponse<Object> apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Xử lý InternalServerException.
	 *
	 * @param ex InternalServerException
	 * @return Phản hồi với {@link ApiResponse} chứa thông tin lỗi.
	 */
	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<ApiResponse<Object>> handleInternal(InternalServerException ex) {
		ApiResponse<Object> apiResponse = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Xử lý MethodArgumentNotValidException.
	 *
	 * @param ex MethodArgumentNotValidException
	 * @return Phản hồi với {@link ApiResponse} chứa thông tin lỗi và chi tiết các trường không hợp lệ.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotvalidResponseEntity(
			MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});

		ApiResponse<Object> apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, "Validation failed", errors);
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Xử lý HttpMessageNotReadableException.
	 *
	 * @param ex HttpMessageNotReadableException
	 * @return Phản hồi với {@link ApiResponse} chứa thông tin lỗi.
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
		int maxRetries = 3;
		Throwable cause = ex.getCause();
		while (cause != null && maxRetries-- > 0) {
			if (cause instanceof BadRequestException) {
				ApiResponse<Object> apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, cause.getMessage());
				return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
			}
			cause = cause.getCause();
		}

		ApiResponse<Object> apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, "Invalid JSON");
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Xử lý DataAccessException
	 * 
	 * @param ex PropertyReferenceException
	 * @return Phản hồi với {@link ApiResponse} chứa thông tin lỗi.
	 */
	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<ApiResponse<Object>> handleDataAccessException(PropertyReferenceException ex) {
		ApiResponse<Object> apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, "Access data failed", ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Xử lý các exception khác chưa được xử lý cụ thể.
	 *
	 * @param ex Exception
	 * @return Phản hồi với {@link ApiResponse} chứa thông tin lỗi.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> handleOther(Exception ex) {
		log.error(ex.getLocalizedMessage());
		ApiResponse<Object> apiResponse = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR,
				"An unexpected internal server error occurred.");
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
