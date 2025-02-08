package com.querypie.config;

import com.querypie.config.ErrorResponse.FieldErrorResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> Exception(Exception e) {
        final ErrorResponse errorResponse = ErrorResponse.create(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "예기치 않은 예외가 발생 했습니다."
        );

        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    protected ResponseEntity<ErrorResponse> BindException(BindException e) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        List<FieldError> fieldErrors = e.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errors.add(new FieldErrorResponse(
                    fieldError.getField(),
                    fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString(),
                    fieldError.getDefaultMessage()
            ));
        }

        final ErrorResponse errorResponse = ErrorResponse.create(
                HttpStatus.BAD_REQUEST,
                "입력한 데이터가 유효하지 않습니다. 다시 확인해 주세요.",
                errors
        );

        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<ErrorResponse> IllegalArgumentException(RuntimeException e) {
        final ErrorResponse errorResponse = ErrorResponse.create(
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        );

        log.info(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Object> HttpRequestMethodNotSupportedException(
            final HttpRequestMethodNotSupportedException e
    ) {
        final ErrorResponse errorResponse = ErrorResponse.create(
                HttpStatus.BAD_REQUEST,
                "HTTP 메서드를 확인해 주세요"
        );
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException e
    ) {
        final ErrorResponse errorResponse = ErrorResponse.create(
                HttpStatus.BAD_REQUEST,
                "요청 파라미터를 확인해 주세요"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex) {
        final ErrorResponse errorResponse = ErrorResponse.create(
                HttpStatus.NOT_FOUND,
                "요청하신 페이지를 찾을 수 없습니다."
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
