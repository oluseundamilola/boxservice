package com.damilola.box_service.utils;


import com.damilola.box_service.DTOs.DefaultResponse;

public class ResponseUtil {

    public static <T> DefaultResponse<T> success(String message, T data) {
        return new DefaultResponse<>("00", message, data);
    }

    public static <T> DefaultResponse<T> failure(String message) {
        return new DefaultResponse<>("99", message, null);
    }
}
