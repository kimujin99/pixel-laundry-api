package com.toy.pixel_laundry_api.exception;

public class MissingParameterException extends IllegalArgumentException {
    public MissingParameterException(String paramName) {
        super("필수 값이 누락되었습니다: " + paramName);
    }
}
