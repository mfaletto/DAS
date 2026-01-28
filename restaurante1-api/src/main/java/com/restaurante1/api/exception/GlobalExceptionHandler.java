package com.restaurante1.api.exception;

import com.restaurante1.api.dto.ClickNotificacionDTO;
import com.restaurante1.api.dto.ErrorResponseDTO;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        
        ErrorResponseDTO error = new ErrorResponseDTO("VALIDATION_ERROR", details);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateKey(DuplicateKeyException ex, jakarta.servlet.http.HttpServletRequest request) {
        // En una implementación real más compleja se podría extraer del body, 
        // pero aquí devolvemos el error 409 según lo solicitado.
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO("DUPLICATE_CLICK", (Integer) null));
    }
}
