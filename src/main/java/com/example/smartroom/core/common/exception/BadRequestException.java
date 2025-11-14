package com.example.smartroom.core.common.exception;

/**
 * Exception cho trường hợp request không hợp lệ.
 */
public class BadRequestException extends RuntimeException {
	
    private static final long serialVersionUID = 7031828663679780344L;

    public BadRequestException() {
        super("Bad Request");
    }

	public BadRequestException(String message) {
        super(message);
    }
}
