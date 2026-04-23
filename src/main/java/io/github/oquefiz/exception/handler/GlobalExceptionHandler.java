package io.github.oquefiz.exception.handler;

import io.github.oquefiz.exception.ConflictException;
import io.github.oquefiz.exception.ForbidenException;
import io.github.oquefiz.exception.NotFoundException;
import io.github.oquefiz.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class GlobalExceptionHandler {

    // 409
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErroResponse> handlerConflict(ConflictException e, HttpServletRequest request){
        return buildResponse(HttpStatus.CONFLICT, e.getMessage(), e.getMessages(), request);
    }

    // 403
    @ExceptionHandler(ForbidenException.class)
    public ResponseEntity<ErroResponse> handlerForbiden(ForbidenException e, HttpServletRequest request){
        return buildResponse(HttpStatus.FORBIDDEN, e.getMessage(), e.getMessages(), request);
    }

    // 404
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroResponse> handlerNotFound(ForbidenException e, HttpServletRequest request){
        return  buildResponse(HttpStatus.NOT_FOUND, e.getMessage(), e.getMessages(), request);
    }

    // 401
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErroResponse> handlerUnauthorized(UnauthorizedException e, HttpServletRequest request){
        return buildResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), e.getMessages(), request);
    }

    private ResponseEntity<ErroResponse> buildResponse(
            HttpStatus status,
            String message,
            List<String> messages,
            HttpServletRequest request
    ){
        ErroResponse erroResponse = ErroResponse.builder()
                .timesTamp(LocalDateTime.now())
                .status(status.value())
                .error(message)
                .path(request.getRequestURI())
                .build();
        return  ResponseEntity.status(status).body(erroResponse);

    }

}
