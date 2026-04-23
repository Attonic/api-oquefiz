package io.github.oquefiz.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * HTTP Status 403
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
@Getter
public class ForbidenException extends RuntimeException{

    private final List<String> messages;

    public ForbidenException(String messge){
        super(messge);
        this.messages = List.of(messge);
    }

    public ForbidenException(List<String> messages){
        super(messages.toString());
        this.messages = messages;
    }

}
