package com.spring.jpa.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.jpa.entity.Guesthouse;

public interface GuesthouseRepository extends JpaRepository<Guesthouse, Integer> {
	
	//모든 게스트하우스 조회
	
	//grade를 가지고 예약이 가능한 게스트하우스들을 반환
	@Query("select gh from Guesthouse gh where gh.grade = :grade")
	List<Guesthouse> getSuitableGuesthouses(@Param("grade") Integer grade);
	
	
	
	//특정 날짜, 예약가능여부 => 서비스레이어에서 필터링 
	//user의 grade에 맞는 게스트하우스들을 보여줌
	//1명이 여러번 예약할 수 없어야 한다.
//	@Query("select gh from Guesthouse ")
//	List<Guesthouse> getGuesthouses(@Param("date")LocalDate date,@Param("user") User user);
//	
	
	
	
}
