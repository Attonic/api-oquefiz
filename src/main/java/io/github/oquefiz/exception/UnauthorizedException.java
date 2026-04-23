package io.github.oquefiz.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Http status 401
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Getter
public class UnauthorizedException extends RuntimeException{

    private final List<String> messages;

    public UnauthorizedException(String message){
        super(message);
        this.messages = List.of(message);
    }

    public UnauthorizedException(List<String> messages){
        super(messages.toString());
        this.messages = messages;
    }

}
