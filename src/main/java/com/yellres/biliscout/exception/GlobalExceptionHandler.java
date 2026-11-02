package com.yellres.biliscout.exception;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.yellres.biliscout.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Result<Void>> handleTaskNotFound(
        TaskNotFoundException ex
    ) {
        return ResponseEntity.status(404).body(
            new Result<>(40401, ex.getMessage(), null)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Void>> methodArgumentNotValid(
        MethodArgumentNotValidException ex
    ) {
        FieldError fieldError = ex.getBindingResult().getFieldError();

        String message = fieldError == null
            ? "请求参数不合法"
            : fieldError.getDefaultMessage();
        return ResponseEntity.status(400).body(
            new Result<>(40001, message, null)
        );
    }

}
