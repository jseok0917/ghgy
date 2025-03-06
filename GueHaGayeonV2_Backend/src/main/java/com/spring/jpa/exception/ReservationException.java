package com.spring.jpa.exception;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationException extends RuntimeException {
    private String title;        // 예외의 제목
    private String message;      // 예외의 상세 메시지
    private HttpStatus httpStatus; // HTTP 상태 코드

    // 기본 생성자
    public ReservationException(String message) {
        this("Reservation Not Found", message, HttpStatus.NOT_FOUND);
    }
    
    public ReservationException(String title, String message) {
		this(title, message, HttpStatus.NOT_FOUND);
    }
}
