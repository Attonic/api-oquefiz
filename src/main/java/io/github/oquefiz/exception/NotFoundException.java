package io.github.oquefiz.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * HTTP status 404
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class NotFoundException extends RuntimeException{

    private final List<String> messages;

    public NotFoundException(String message){
        super(message);
        this.messages = List.of(message);
    }

    public NotFoundException(List<String> messages){
        super(messages.toString());
        this.messages = messages;
    }

}
