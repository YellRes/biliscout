package com.yellres.biliscout.common;

public record Result<T>(
    int code,
    String message,
    T data
) {}
