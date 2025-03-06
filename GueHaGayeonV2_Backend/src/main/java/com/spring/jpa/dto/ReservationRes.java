package com.spring.jpa.dto;

import java.time.LocalDate;

import com.spring.jpa.entity.Guesthouse;
import com.spring.jpa.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ReservationRes {

	//게스트하우스 이름
	private String name;
	
	//지역
	private String location;
	
	//가격(단위 : 원)
	private Integer price;
	
	private Integer reservationId;
	
	//체크인날짜
	@Column
	private LocalDate checkin;
	

}
