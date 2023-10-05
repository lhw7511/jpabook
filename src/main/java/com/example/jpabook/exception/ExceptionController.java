package com.example.jpabook.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String,String>> ExcetionHandler(Exception e){
        log.info("전역 컨트롤러에서 예외처리");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("error type", httpStatus.getReasonPhrase());
        resultMap.put("code","400");
        resultMap.put("message","에러발생");

        return ResponseEntity.status(httpStatus).body(resultMap);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public String nullHandler(Exception e){
        return "null";
    }
}
