package com.example.smartroom.common.exception;

/**
 * Exception cho trường hợp không tìm thấy tài nguyên.
 */
public class NotFoundException extends RuntimeException {
	
    private static final long serialVersionUID = 6811210395347389238L;

    public NotFoundException() {
        super("Not Found");
    }

	public NotFoundException(String message) {
        super(message);
    }
}
