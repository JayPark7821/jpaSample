package com.example.giggaldemo.common;

import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE) // 우선순위를 가장 높게
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleCustomIllegalArgumentException(IllegalArgumentException ex) {
		// 추후 slack 에러 로그 연동 로직 추가 가능??!!
		return  new ResponseEntity<>(DefaultRes.response(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.OK);
	}
}