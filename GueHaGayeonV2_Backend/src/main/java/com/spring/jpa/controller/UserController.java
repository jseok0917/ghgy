package com.spring.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.jpa.dto.UserReq;
import com.spring.jpa.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequiredArgsConstructor
@Tag(name = "유저 관리", description = "UserController")
public class UserController {
	
		@Autowired
		private final UserService uService;
	
		//유저 조회
		@GetMapping("users/{userId}")
		@Operation(summary = "유저 조회", description = "유저 아이디로 유저를 조회합니다.")
		public ResponseEntity<?> getUserById(@PathVariable Integer userId) {
			return ResponseEntity.status(200)
					.body(uService.getUserById(userId));
		}
		
		
		//유저 프로필 등록
		@PostMapping("users")
		@Operation(summary = "회원가입", description = "성별, 나이, 키, 몸무게, 취미, mbti, 결혼 여부, 연봉을 입력하여 회원가입합니다.")
		public ResponseEntity<?> registerUser(@RequestBody UserReq userReq) {

			return ResponseEntity.status(201)
					.body(uService.registerUser(userReq));
		}
		
		//로그인
		@PostMapping("users/login")
		@Operation(summary = "로그인", description = "유저 아이디를 입력하여 로그인합니다.")
		public ResponseEntity<?> signIn(@RequestBody UserReq userReq) throws Exception {
		
			return ResponseEntity.status(200)
					.body(uService.signIn(userReq));
		}
		
		//회원정보 수정
		@PutMapping("users/update")
		@Operation(summary = "회원 정보 수정", description = "유저 아이디를 입력하여 로그인합니다.")
		public ResponseEntity<?> updateInfo(@RequestBody UserReq userReq) throws Exception {
		
			return ResponseEntity.status(200)
					.body(uService.updateUser(userReq));
		}

}
