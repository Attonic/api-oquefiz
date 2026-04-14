package io.github.exception.handler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErroResponse {

    private LocalDateTime timesTamp;
    private int status;
    private String error;
    private String message;
    private List<String> messages;
    private String path;

}
