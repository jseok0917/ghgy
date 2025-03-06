package com.spring.jpa.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spring.jpa.dto.GuesthouseRes;
import com.spring.jpa.dto.ReservationRes;
import com.spring.jpa.entity.Guesthouse;
import com.spring.jpa.entity.Reservation;
import com.spring.jpa.entity.User;
import com.spring.jpa.exception.ReservationException;
import com.spring.jpa.repository.GuesthouseRepository;
import com.spring.jpa.repository.ReservationRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
	
	@Autowired
	private final GuesthouseRepository ghRepository;
	
	@Autowired
	private final ReservationRepository rRepository;

	// 이거 가운데 gh변수 삭제됨
	// gh => DTO에 담아서
	
	@Transactional
	//특정유저가 특정날짜의 예약가능한 GuesthouseRes를 반환, Guesthouse에는 현재 수용인원 정보가 없음
	public List<GuesthouseRes> getAvailableGuesthouse(LocalDate date, User user) {

		// 유저의 등급
		Integer grade = user.getGrade();

		// 유저 등급에 맞는 게스트하우스 선별
		List<GuesthouseRes> ghList = new ArrayList<>();

		// 선별된 각 게스트하우스마다 예약가능한 게스트하우스들만 반환할 리스트에 넣기
		ghRepository.getSuitableGuesthouses(grade).forEach(gh -> {
			// 최대 수용인원
			Integer capacity = gh.getCapacity(); // 이건 남녀 각각 동일, 그래서 게하 전체 수용인원은 capacity*2임
			// 게스트하우스 ID
			Integer ghId = gh.getGhId();

			// 주어진 날짜에 예약자와 성별이 같은 사람들
			// 현재 몇명이나 예약한 상태인지 계산
			Integer currentCapacity = rRepository.getCurrentCapacity(date, ghId, user.getGender());

			// 예약가능하면 리스트에넣기
			if (capacity > currentCapacity) {
				GuesthouseRes ghResponse = gh.toGuesthouseRes(gh, currentCapacity);
				ghList.add(ghResponse);
			}
		});

		return ghList;
	}

	// 예외처리는 나중에 생각

	//
//	// 예약가능한 인원이 남아있는지 확인
//	@Transactional
//	public boolean isAvailableReservation(LocalDate date, Guesthouse gh, User user) {
//		// 최대 수용인원
//		Integer capacity = gh.getCapacity(); // 이건 남녀 각각 동일, 그래서 게하 전체 수용인원은 capacity*2임
//		Integer ghId = gh.getGhId();
//
//		// 해당 게스트하우스에 현재 예약한 인원 수 조회
//		// 이거 다른사람이 예약등록중에 조회해버리면 팬텀리드 문제가 생길 수도 있다.
//		// 그래서 예약할 때 인원이 남아있는지 한번 더 체크 => 이것도 팬텀리드 생길 수 있음.
//		// 예약테이블은 Serializable로 설정? => 이러면 읽기성능에 심각한 저하,,
//		Integer currentCapacity = rRepository.getCurrentCapacity(date, ghId, user.getGender());
//
//		// 예약가능하면 true
//		// 불가능하면 false
//		return (capacity > currentCapacity);
//	}


	// =====================여기 수정=========================

	@Transactional
	// 유저가 예약한 모든 예약을 조회하는 기능
	//예약 res : 
	public List<ReservationRes> getReservations(Integer userId) {
		
		List<ReservationRes> rResList = new ArrayList<>();
		
		rRepository.getReservations(userId).forEach(r-> {
			
			rResList.add(r.toReservationRes(r));
		});
		
		
		return rResList;
	};

	@Transactional
	// 날짜와 특정 게스트하우스, 성별을 가지고, 해당 게스트하우스에 현재 예약한 인원 리턴
	public Integer getCurrentCapacity(LocalDate date, Integer ghId, Integer gender) {
		return rRepository.getCurrentCapacity(date, ghId, gender);
	};

	@Transactional
	// 게스트하우스랑, 날짜를 선택했을 때
	// 해당 날짜에 예약한 유저들을 전부 보여주는 기능
	public List<User> getUsers(LocalDate date, Integer ghId) {
		return rRepository.getUsers(date, ghId);
	};

	// CRRUD

//	@Transactional
//	//예약 등록
//	//예약할 날짜, 유저id, 게스트하우스id
//	public synchronized Reservation createReservation(LocalDate date, User user, Guesthouse gh) throws Exception {
//		
//		//인원이 남아있는지 체크
//		if (!isAvailableReservation(date, gh, user)) {
//			throw new ReservationException("Title - 예약 에러", "Message - 예약 가능한 인원을 초과했습니다.", HttpStatus.BAD_REQUEST);
//		}
//		
//		//중복된 예약이 없는경우 
//		Reservation r = Reservation.builder()
//				.user(user)
//				.gh(gh)
//				.checkin(date)
//				.build();
//		
//		return rRepository.save(r);
//	}
	
	
	
	//예약 등록에 대한 동시 처리를 위해 synchronized
//	@Transactional <-> 
// 메서드가 완료되고 Commit이 되는 시점 사이에 메서드가 또 돌 수가 있다.
// synchronized를 적용했을 때도, 작업이 하나이기때문에 Transactional을 쓰지않아도 일단은 괜찮음
//**단일 서버라 이렇게 할 수 있다
	public synchronized Reservation createReservation(LocalDate date, User user, Guesthouse gh) throws Exception {
		// 동일 유저 예약 중복체크 => 이건 DB단에서 UNIQUE 제약조건으로 설정
		
		// 최대 수용인원
		Integer capacity = gh.getCapacity(); // 이건 남녀 각각 동일, 그래서 게하 전체 수용인원은 capacity*2임
		Integer ghId = gh.getGhId();

		// 해당 게스트하우스에 현재 예약한 인원 수 조회
		Integer currentCapacity = rRepository.getCurrentCapacity(date, ghId, user.getGender());
		
		// 인원이 남아있는지 체크
		if (capacity > currentCapacity) {
		
			// 인원이 남아있을 경우
			Reservation r = Reservation.builder().user(user).gh(gh).checkin(date).build();
			try {
	            return rRepository.save(r);
	        } catch (DataIntegrityViolationException e) {
	        	System.out.println(e.getLocalizedMessage());
	            throw new ReservationException("예약 에러", "중복된 예약은 불가능합니다.", HttpStatus.BAD_REQUEST);
	        }
		} else {
			throw new ReservationException("Title - 예약 에러", "Message - 예약 가능한 인원을 초과했습니다.", HttpStatus.BAD_REQUEST);
		}

	}
	
	
	//예약 취소
	@Transactional
	public String deleteReservation(Integer reservationId) {
		
		Reservation r = rRepository.findById(reservationId).orElse(null);
		
		if (r == null) {
			throw new ReservationException();
		} else {
			rRepository.delete(r);
			return "OK";
		}
	}
	
}
