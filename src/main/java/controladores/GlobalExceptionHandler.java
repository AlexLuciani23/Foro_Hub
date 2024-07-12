package com.example.ForoHub.excepciones; // Asegúrate de que el paquete sea el correcto

import com.example.ForoHub.excepcionesAplicacion.ErrorDeValidacionException;
import com.example.ForoHub.excepcionesAplicacion.ForoHubException;
import com.example.ForoHub.excepcionesAplicacion.TopicoNoEncontradoException;
import com.example.ForoHub.excepcionesAplicacion.UsuarioNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org
        .springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<String> handleUsuarioNoEncontradoException(UsuarioNoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TopicoNoEncontradoException.class)
    public ResponseEntity<String> handleTopicoNoEncontradoException(TopicoNoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ErrorDeValidacionException.class)
    public ResponseEntity<String> handleErrorDeValidacionException(ErrorDeValidacionException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Manejador genérico para otras excepciones de ForoHub
    @ExceptionHandler(ForoHubException.class)
    public ResponseEntity<String> handleForoHubException(ForoHubException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
