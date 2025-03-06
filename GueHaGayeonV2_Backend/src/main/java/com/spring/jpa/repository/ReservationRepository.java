package com.spring.jpa.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.jpa.entity.Reservation;
import com.spring.jpa.entity.User;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	
	//모든 예약 조회하는 기능
	@Query("select r from Reservation r where r.user.userId = :userId")
	List<Reservation> getReservations(@Param("userId") Integer userId);
	
	//날짜와 특정 게스트하우스, 성별을 가지고, 해당 게스트하우스에 현재 예약한 인원 리턴
	@Query("select count(*) from Reservation r join r.user u where r.gh.ghId = :ghId and r.checkin = :date and u.gender = :gender")
	Integer getCurrentCapacity(@Param("date") LocalDate date, @Param("ghId") Integer ghId, @Param("gender") Integer gender);
	
	//게스트하우스랑, 날짜를 선택했을 때
	//해당 날짜에 예약한 유저들을 전부 보여주는 기능
	@Query(value = "select u from Reservation r join r.user u where r.checkin = :date and r.gh.ghId = :ghId")
	List<User> getUsers(LocalDate date, Integer ghId);
	
	//예약 중복확인
    @Query(value = "SELECT r FROM Reservation r WHERE r.checkin = :date AND r.user.userId = :userId AND r.gh.ghId = :ghId ")
    Reservation duplicateCheck(LocalDate date, Integer userId, Integer ghId);
	

}
