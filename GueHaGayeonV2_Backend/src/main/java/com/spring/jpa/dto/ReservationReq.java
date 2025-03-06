package com.spring.jpa.dto;

import com.spring.jpa.entity.Reservation;

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
public class ReservationReq {

	private Integer userId;
	private Integer ghId;
	private String date;
	

	

}
