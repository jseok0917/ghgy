package com.spring.jpa.advice;

import java.time.LocalDateTime;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.jpa.exception.DMLException;
import com.spring.jpa.exception.GuesthouseException;
import com.spring.jpa.exception.ReservationException;
import com.spring.jpa.exception.UserException;

@RestControllerAdvice
public class DefaultExceptionAdvice {

    @ExceptionHandler(GuesthouseException.class)
    public ProblemDetail guesthouseExceptionHandler(GuesthouseException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(404);
        problemDetail.setTitle(e.getTitle());
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(ReservationException.class)
    public ProblemDetail reservationExceptionHandler(ReservationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(404);
        problemDetail.setTitle(e.getTitle());
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }


    @ExceptionHandler(UserException.class)
    public ProblemDetail userExceptionHandler(UserException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(401);
        problemDetail.setTitle(e.getTitle());
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }
    
	@ExceptionHandler(DMLException.class)
	public ProblemDetail DMLExceptionHandle(DMLException e) {
		ProblemDetail problemDetail = ProblemDetail.forStatus(e.getHttpStatus().value());
		problemDetail.setTitle(e.getTitle());
		problemDetail.setDetail(e.getMessage());
		problemDetail.setProperty("timestamp", LocalDateTime.now());
		return problemDetail;	
	}

    @ExceptionHandler(Exception.class)
    public ProblemDetail exceptionHandler(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(500);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail("An unexpected error occurred.");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }
}
