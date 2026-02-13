package ar.edu.ubp.das.restaurante.labellapizza.exception;

import ar.edu.ubp.das.restaurante.labellapizza.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Atrapa cualquier error genérico (NullPointer, etc.)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> manejarErrorGeneral(Exception ex) {
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno no controlado: " + ex.getMessage(),
                "La Bella Pizza Backend"
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Aquí podrías agregar más métodos para atrapar excepciones específicas
    // Ejemplo: RecursoNoEncontradoException, etc.
}