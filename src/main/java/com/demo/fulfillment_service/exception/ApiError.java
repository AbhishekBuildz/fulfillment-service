package com.demo.fulfillment_service.exception;

public record ApiError(
        int status,
        String message,
        String timestamp
) {}
