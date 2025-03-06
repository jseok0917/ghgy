package com.spring.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.jpa.entity.Guesthouse;
import com.spring.jpa.service.GuesthouseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequiredArgsConstructor
@Tag(name = "게스트하우스 관리", description = "GuesthouseController")
public class GuesthouseController {

	@Autowired
	private final GuesthouseService ghService;
	
	//CRRUD
	//전체 게스트하우스 검색
	@GetMapping("/guesthouses")
	@Operation(summary = "전체 게스트하우스 조회", description = "전체 게스트하우스 목록을 조회합니다.")
	public ResponseEntity<?> getGuesthouse() {
		return ResponseEntity.status(200)
				.body(ghService.getAllGuesthouses());
	}
	
	//게스트하우스 번호로 특정 게스트하우스 조회
	@GetMapping("/guesthouses/{ghId}")
	@Operation(summary = "게스트하우스 조회", description = "게스트하우스 아이디를 입력하여 특정 게스트하우스를 조회합니다.")
	public ResponseEntity<?> getGuesthouse(@PathVariable Integer ghId) {
		return ResponseEntity.status(200)
				.body(ghService.getGuesthouse(ghId));
	}
	
	//게스트하우스 번호, date, 유저성별로 특정 게스트하우스 조회
	@GetMapping("/guesthouses/current")
	@Operation(summary = "게스트하우스 현재 현황 조회", description = "게스트하우스 번호, date, 유저성별로 특정 게스트하우스 정보와 예약 인월을 조회합니다.")
	public ResponseEntity<?> getCurrentGuesthouse(@RequestParam String date, @RequestParam Integer ghId, @RequestParam Integer gender) {
		return ResponseEntity.status(200)
				.body(ghService.getCurrentGuesthouse(date, ghId, gender));
	}
	
//	//게스트하우스 등록(미구현)
//	@PostMapping("/guesthouses/register")
//	public Guesthouse createGuesthouse(Guesthouse gh) {
//		return 
//	}
	
	
	
}
