package com.spring.jpa.dto;

import com.spring.jpa.entity.Guesthouse;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuesthouseRes {
	
	//PK
	private Integer ghId;
	
	//게스트하우스 이름
	private String name;
	
	//지역
	private String location;
	
	//가격(단위 : 원)
	private Integer price;
	
	//게스트하우스 등급
	private Integer grade;
	
	//수용할 수 있는 인원
	private Integer capacity;
	
	//게스트하우스 이미지 경로
	private String ghImgURL;
	
	//현재 예약 인원
	private Integer currentReservationCount;
	

}
