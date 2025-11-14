package com.example.smartroom.core.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.smartroom.core.common.vo.ApiResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * Xử lý các exception toàn cục cho ứng dụng. Trả về cấu trúc
 * {@link ApiResponseBody} thống nhất.
 */
@RestControllerAdvice(
	basePackages = { 
		"com.example.smartroom.modules"
	}
)
@Slf4j
public class GlobalApiExceptionHandler {
	/**
	 * Xử lý NotFoundException.
	 *
	 * @param ex NotFoundException
	 * @return Phản hồi với {@link ApiResponseBody} chứa thông tin lỗi.
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiResponseBody<Object>> handleNotFound(NotFoundException ex) {
		ApiResponseBody<Object> apiResponse = ApiResponseBody.error(HttpStatus.NOT_FOUND, ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * Xử lý BadRequestException.
	 *
	 * @param ex BadRequestException
	 * @return Phản hồi với {@link ApiResponseBody} chứa thông tin lỗi.
	 */
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiResponseBody<Object>> handleBadRequest(BadRequestException ex) {
		ApiResponseBody<Object> apiResponse = ApiResponseBody.error(HttpStatus.BAD_REQUEST, ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Xử lý InternalServerException.
	 *
	 * @param ex InternalServerException
	 * @return Phản hồi với {@link ApiResponseBody} chứa thông tin lỗi.
	 */
	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<ApiResponseBody<Object>> handleInternal(InternalServerException ex) {
		ApiResponseBody<Object> apiResponse = ApiResponseBody.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Xử lý MethodArgumentNotValidException.
	 *
	 * @param ex MethodArgumentNotValidException
	 * @return Phản hồi với {@link ApiResponseBody} chứa thông tin lỗi và chi tiết các trường không hợp lệ.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponseBody<Object>> handleMethodArgumentNotvalidResponseEntity(
			MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});

		ApiResponseBody<Object> apiResponse = ApiResponseBody.error(HttpStatus.BAD_REQUEST, "Validation failed", errors);
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Xử lý HttpMessageNotReadableException.
	 *
	 * @param ex HttpMessageNotReadableException
	 * @return Phản hồi với {@link ApiResponseBody} chứa thông tin lỗi.
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponseBody<Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
		int maxRetries = 3;
		Throwable cause = ex.getCause();
		while (cause != null && maxRetries-- > 0) {
			if (cause instanceof BadRequestException) {
				ApiResponseBody<Object> apiResponse = ApiResponseBody.error(HttpStatus.BAD_REQUEST, cause.getMessage());
				return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
			}
			cause = cause.getCause();
		}

		ApiResponseBody<Object> apiResponse = ApiResponseBody.error(HttpStatus.BAD_REQUEST, "Invalid JSON");
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Xử lý DataAccessException
	 * 
	 * @param ex PropertyReferenceException
	 * @return Phản hồi với {@link ApiResponseBody} chứa thông tin lỗi.
	 */
	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<ApiResponseBody<Object>> handleDataAccessException(PropertyReferenceException ex) {
		ApiResponseBody<Object> apiResponse = ApiResponseBody.error(HttpStatus.BAD_REQUEST, "Access data failed", ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Xử lý các exception khác chưa được xử lý cụ thể.
	 *
	 * @param ex Exception
	 * @return Phản hồi với {@link ApiResponseBody} chứa thông tin lỗi.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseBody<Object>> handleOther(Exception ex) {
		log.error(ex.getLocalizedMessage());
		ApiResponseBody<Object> apiResponse = ApiResponseBody.error(HttpStatus.INTERNAL_SERVER_ERROR,
				"An unexpected internal server error occurred.");
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
