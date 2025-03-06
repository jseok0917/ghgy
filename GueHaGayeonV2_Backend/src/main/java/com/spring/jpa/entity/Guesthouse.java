package com.spring.jpa.entity;

import com.spring.jpa.dto.GuesthouseRes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Guesthouse {
	
	@Id //PK
	@Column(name="gh_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	//DTO로 변환
	//gh에 현재 예약인원을 추가
		public GuesthouseRes toGuesthouseRes(Guesthouse gh, Integer currentReservationCount) {
			GuesthouseRes response = GuesthouseRes.builder()
					.capacity(gh.getCapacity())
					.ghId(gh.getGhId())
					.ghImgURL(gh.getGhImgURL())
					.grade(gh.getGrade())
					.location(gh.getLocation())
					.name(gh.getName())
					.price(gh.getPrice())
					.currentReservationCount(currentReservationCount)
					.build();
			
			return response;
		}


	@Override
	public String toString() {
		return "Guesthouse [ghId=" + ghId + ", name=" + name + ", location=" + location + ", price=" + price
				+ ", grade=" + grade + ", capacity=" + capacity + "]";
	}
	
	

}
