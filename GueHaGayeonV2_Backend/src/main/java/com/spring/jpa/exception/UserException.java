package com.spring.jpa.exception;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserException extends RuntimeException {
    private String title;        // 예외의 제목
    private String message;      // 예외의 상세 메시지
    private HttpStatus httpStatus; // HTTP 상태 코드

    // 기본 생성자
    public UserException(String message) {
        this("User Authentication Failed", message, HttpStatus.UNAUTHORIZED);
    }
    
    public UserException(String title, String message) {
		this(title, message, HttpStatus.UNAUTHORIZED);
    }
}
