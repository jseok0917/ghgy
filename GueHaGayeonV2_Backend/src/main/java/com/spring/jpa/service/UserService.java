package com.spring.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spring.jpa.dto.UserReq;
import com.spring.jpa.entity.User;
import com.spring.jpa.exception.UserAuthenticationException;
import com.spring.jpa.exception.UserException;
import com.spring.jpa.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final ScoreService scoreService;

	//grade 산정 로직 => 너무 복잡해서 ScoreService로 클래스 분리
	
	//유저 조회
	@Transactional
	public User getUserById(Integer userId) {
		return userRepository.findById(userId).orElse(null);
	}
	
	//===========================추가===============================
	
	
	//유저 등록
	@Transactional
	public User registerUser(UserReq userReq) {
		
		//트랜잭션안에 트랜잭션이 들어가지만
		//트랜잭션 전파에 의해서 하나의 트랜잭션으로 처리됨
		
		//유저의 프로필 정보를 바탕으로 유저의 등급을 계산
		Integer grade = scoreService.calculateScore(userReq);
		//유저DTO를 유저Entity로 변환
		User u = userReq.toUser(userReq, grade);
		
		return userRepository.save(u);
	}
	
	
	//로그인
	@Transactional
	public User signIn(UserReq userReq) throws Exception {
		User user = userRepository.getUser(userReq.getId()).orElseThrow(() -> new UserException("Title - 로그인 오류", "Message - 해당 유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
		
		if (user.getPassword().equals(userReq.getPassword()))  {
			return user;
		} else {
			throw new UserAuthenticationException("비밀번호가 틀렸습니다.");
		}
	}
	
	//회원정보수정, grade 다시계산
	@Transactional
	public User updateUser(UserReq userReq) throws Exception {
		
		//예외처리
		User u = userRepository.getUser(userReq.getId()).orElse(null);
		if (u == null) {
			throw new UserAuthenticationException();
		}
		
		Integer grade = scoreService.calculateScore(userReq);
		u.setGrade(grade);
		u.setId(userReq.getId());
		u.setPassword(userReq.getPassword());
		u.setGender(userReq.getGender());
		u.setAge(userReq.getAge());
		u.setHeight(userReq.getHeight());
		u.setWeight(userReq.getWeight());
		u.setMbti(userReq.getMbti());
		u.setSalary(userReq.getSalary());
		u.setStatus(userReq.getStatus());
		u.setHobby(userReq.getHobby());
		
		return u;
	}

}
