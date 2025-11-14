package com.example.smartroom.core.common.exception;

/**
 * Exception cho lỗi hệ thống nội bộ.
 */
public class InternalServerException extends RuntimeException {
	
    private static final long serialVersionUID = -4507069552456491335L;

    public InternalServerException() {
        super("Internal Server Error");
    }

	public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
