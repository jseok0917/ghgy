package com.spring.jpa.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spring.jpa.dto.GuesthouseRes;
import com.spring.jpa.entity.Guesthouse;
import com.spring.jpa.exception.GuesthouseException;
import com.spring.jpa.repository.GuesthouseRepository;
import com.spring.jpa.repository.ReservationRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuesthouseService {
	
	@Autowired
	private final GuesthouseRepository ghRepository;
	
	@Autowired
	private final ReservationRepository rRepository;
	
	
	@Transactional
	//전체 게스트하우스 검색
	public List<Guesthouse> getAllGuesthouses() {
		return ghRepository.findAll();
	}
	
	//===========================추가===============================
	
	
	@Transactional
	//grade를 가지고 예약이 가능한 게스트하우스들을 반환
	public List<Guesthouse> findGuesthouses(Integer grade) {
		return ghRepository.getSuitableGuesthouses(grade);
	};
	
	//CRRUD
	
	@Transactional
	//게스트하우스 등록
	public Guesthouse createGuesthouse(Guesthouse gh) {
		return ghRepository.save(gh);
	}
	
	
	@Transactional
	//게스트하우스 조회
	public Guesthouse getGuesthouse(Integer ghId) {
		return ghRepository.findById(ghId).orElseThrow(
				// 조회 오류 시 에러
				() -> new GuesthouseException("Title - 검색 에러", "Message - 게스트하우스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

	}
	
	@Transactional
	//게스트하우스 정보 및 현재 예약인원 조회
	public GuesthouseRes getCurrentGuesthouse(String date, Integer ghId, Integer gender) {
		
		LocalDate lDate = LocalDate.parse(date);
		
		Integer currentCapacity = rRepository.getCurrentCapacity(lDate, ghId, gender);
		
		Guesthouse gh = ghRepository.findById(ghId).orElseThrow(
				// 조회 오류 시 에러
				() -> new GuesthouseException("Title - 검색 에러", "Message - 게스트하우스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
		
		return gh.toGuesthouseRes(gh, currentCapacity);

	}

	
}
