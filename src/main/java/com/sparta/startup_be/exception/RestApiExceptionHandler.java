package com.sparta.startup_be.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestApiExceptionHandler {

    //ExceptionHandler 를 통해 Global 예외 처리 . IllegalArgumentException,NullPointerException 발생시
    // 오류에 해당하는 msg 와 400 코드가 가도록 설정 .

    // illegalArgumentException이 떴을 때 여기서 관리하겠다
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StatusMessage> handleApiRequestException(IllegalArgumentException ex) {
        StatusMessage message = new StatusMessage();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ex.printStackTrace();
        message.setStatusCode(StatusMessage.StatusEnum.BAD_REQUEST);
        message.setMessage(ex.getMessage());

        return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<StatusMessage> handleApiRequestException(NullPointerException ex) {
        StatusMessage message = new StatusMessage();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatusCode(StatusMessage.StatusEnum.BAD_REQUEST);
        message.setMessage(ex.getMessage());
        ex.printStackTrace();


        return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<StatusMessage> handleApiRequestException(IOException ex) {
        StatusMessage message = new StatusMessage();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        ex.printStackTrace();

        message.setStatusCode(StatusMessage.StatusEnum.BAD_REQUEST);
        message.setMessage(ex.getMessage());

        return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("statusCode", String.valueOf(HttpStatus.BAD_REQUEST));
        ex.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        ex.printStackTrace();

        return ResponseEntity.badRequest().body(errors);
    }
}