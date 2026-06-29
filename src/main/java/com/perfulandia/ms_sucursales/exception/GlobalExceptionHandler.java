package com.perfulandia.ms_sucursales.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

// CAPA EXCEPTION - manejo global de errores
// @RestControllerAdvice: el "guardia" de errores de TODOS los controllers.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Atrapa "recurso no encontrado" y responde 404 con mensaje claro
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarNoEncontrado(RecursoNoEncontradoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // 404
    }

    // Atrapa errores de validación (@NotBlank, @NotNull, @Min...) y responde 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidacion(MethodArgumentNotValidException e) {
        Map<String, String> errores = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(campo ->
                errores.put(campo.getField(), campo.getDefaultMessage())
        );
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST); // 400
    }

    // Atrapa cualquier otro error no previsto y responde 500
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> manejarGeneral(RuntimeException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    // Atrapa cuando el JSON está mal formado (una coma de más, llave sin cerrar, etc.)
    // Responde 400 con un mensaje claro en vez del error feo de parseo.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarJsonMalFormado(HttpMessageNotReadableException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "El cuerpo de la solicitud no es un JSON válido o no coincide con la estructura esperada");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // 400
    }

    // Atrapa errores de integridad de la BD (ej: foreign key, dato duplicado).
    // Responde 409 Conflict en vez del 500 genérico.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> manejarIntegridadDatos(DataIntegrityViolationException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "El recurso ya existe o viola una restricción de la base de datos");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT); // 409
    }


}
