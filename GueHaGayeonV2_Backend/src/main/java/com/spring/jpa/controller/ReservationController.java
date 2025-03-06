package com.spring.jpa.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.jpa.dto.ReservationReq;
import com.spring.jpa.entity.Guesthouse;
import com.spring.jpa.entity.User;
import com.spring.jpa.service.GuesthouseService;
import com.spring.jpa.service.ReservationService;
import com.spring.jpa.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequiredArgsConstructor
@Tag(name = "예약 관리", description = "ReservationController")
public class ReservationController {
	
	private final ReservationService rService;
	private final UserService uService;
	private final GuesthouseService ghService;
	
	//특정유저가 특정날짜의 예약가능한 Guesthouse를 반환
	@GetMapping("reservations/available")
	@Operation(summary = "예약가능한 게스트하우스 조회", description = "날짜와 유저 아이디를 입력하여 특정 유저가 특정 날짜에 예약이 가능한 게스트하우스들을 조회합니다.")
	public ResponseEntity<?> getAvailableGuesthouse(@RequestParam LocalDate date,@RequestParam Integer userId) {
		
		User user = uService.getUserById(userId);
		
		return ResponseEntity.status(200)
				.body(rService.getAvailableGuesthouse(date, user));
	}
	
	
	//유저가 예약한 모든 예약을 조회하는 기능
	@GetMapping("reservations/{userId}")
	@Operation(summary = "예약 내역 조회", description = "유저 아이디를 입력하여 특정 유저의 예약 내역을 조회합니다.")
	public ResponseEntity<?> getReservations(@PathVariable Integer userId) {
		
		
		return ResponseEntity.status(200)
				.body(rService.getReservations(userId));
	};
	
	
	//게스트하우스랑, 날짜를 선택했을 때
	//해당 날짜에 예약한 유저들을 전부 보여주는 기능
	@GetMapping("reservations")
	@Operation(summary = "게스트하우스 예약 유저 프로필 조회", description = "날짜와 게스트하우스 아이디를 입력하여 특정 날짜 특정 게스트하우스에 예약한 유저들을 조회합니다.")
	public ResponseEntity<?> getUsers(@RequestParam String date,@RequestParam Integer ghId) {
		
		LocalDate localDate = LocalDate.parse(date);
		return ResponseEntity.status(200)
				.body(rService.getUsers(localDate, ghId));
	};
	

	//예약 등록
	@PostMapping("reservations")
	@Operation(summary = "예약하기", description = "날짜, 유저 아이디, 게스트하우스 아이디를 입력하여 예약을 실행합니다.")
	public ResponseEntity<?> registerReservation(@RequestBody ReservationReq reservationReq) throws Exception {
		
		User user = uService.getUserById(reservationReq.getUserId());
		Guesthouse gh = ghService.getGuesthouse(reservationReq.getGhId());
		LocalDate localDate = LocalDate.parse(reservationReq.getDate());
		
		return ResponseEntity.status(201)
				.body(rService.createReservation(localDate, user, gh));
	}
	
	//예약 취소
	@DeleteMapping("reservations/{reservationId}")
	@Operation(summary = "예약취소", description = "예약id를 이용하여 예약을 취소합니다.")
public ResponseEntity<?> deleteReservation(@PathVariable Integer reservationId) throws Exception {
		
		
		return ResponseEntity.status(200)
				.body(rService.deleteReservation(reservationId));
	}

}
