package org.c4marathon.assignment.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.c4marathon.assignment.global.exception.customs.ExpiredLinkException;
import org.c4marathon.assignment.global.exception.customs.NotFoundException;
import org.c4marathon.assignment.global.response.ApiResponse;
import org.c4marathon.assignment.global.response.enums.ResultCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(NotFoundException e) {
        log.error("Entity Not Found Exception Occurred: {}", e.getMessage());
        return ResponseEntity.status(404).body(ApiResponse.failure(ResultCode.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Entity Illegal Argument Exception Occurred: {}", e.getMessage());
        return ResponseEntity.status(400).body(ApiResponse.failure(ResultCode.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(value = ExpiredLinkException.class)
    public ResponseEntity<ApiResponse<Void>> handleExpiredLinkException(ExpiredLinkException e) {
        log.info("Entity Expired Link Exception Occurred: {}", e.getMessage());
        return ResponseEntity.status(403).body(ApiResponse.failure(ResultCode.FORBIDDEN, e.getMessage()));
    }

}
