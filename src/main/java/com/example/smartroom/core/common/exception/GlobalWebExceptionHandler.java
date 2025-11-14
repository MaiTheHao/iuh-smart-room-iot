package com.example.smartroom.core.common.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Xử lý các exception toàn cục cho các controller render view (sử dụng Thymeleaf).
 * Chuyển hướng người dùng đến các trang lỗi tương ứng.
 */
@ControllerAdvice(basePackages = "com.example.smartroom.administration_ui")
public class GlobalWebExceptionHandler {

	/**
	 * Xử lý lỗi 404 (Không tìm thấy trang).
	 * Được kích hoạt khi một yêu cầu không khớp với bất kỳ controller nào.
	 *
	 * @param ex      NoHandlerFoundException
	 * @param model   Model để truyền dữ liệu tới view.
	 * @return Tên view của trang lỗi 404.
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public String handleNotFound(NoHandlerFoundException ex, Model model) {
		model.addAttribute("errorMessage", "Trang không tồn tại!");
		return "error/404";
	}

	/**
	 * Xử lý tất cả các exception khác chưa được xử lý cụ thể.
	 * Ghi lại lỗi và chuyển hướng đến trang lỗi 500 chung.
	 *
	 * @param ex      Exception
	 * @param model   Model để truyền dữ liệu tới view.
	 * @return Tên view của trang lỗi 500.
	 */
	@ExceptionHandler(Exception.class)
	public String handleOther(Exception ex, Model model) {
		model.addAttribute("errorMessage", "Đã xảy ra lỗi nội bộ!");
		return "error/500";
	}
}
