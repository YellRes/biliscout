package com.yellres.biliscout.exception;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.yellres.biliscout.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Result<Void>> methodArgumentMismatch(
        MethodArgumentTypeMismatchException ex
    ) {
        String message = ex.getName() + " = " + ex.getValue() + " doesnt match";
        return ResponseEntity.status(400).body(
            new Result<>(40002, message, null)
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Result<Void>> httpMessageNotReadable(
        HttpMessageNotReadableException ex
    ) {
        String message = "请求体格式不正确";
        return ResponseEntity.status(400).body(
            new Result<>(40003, message, null)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> commonException(
        Exception ex
    ) {
        log.error("未处理的异常", ex);
        return ResponseEntity.status(500).body(
            new Result<>(50001, "服务器内部错误，请稍后重试", null)
        );
    }

}
