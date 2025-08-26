package com.toy.pixel_laundry_api.exception;

public class InvalidParameterFormatException extends IllegalArgumentException {
    public InvalidParameterFormatException(String paramName, String expectedFormat) {
        super(paramName + "의 형식이 올바르지 않습니다. 예상 형식: " + expectedFormat);
    }
}
