package com.spring.jpa.entity;

import java.time.LocalDate;

import com.spring.jpa.dto.ReservationRes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservation",
uniqueConstraints = @UniqueConstraint(
    name = "unique_checkin_gh_user",
    columnNames = {"checkin", "gh_id", "user_id"}
)
)
@Builder
@AllArgsConstructor
@NoArgsConstructor 
@Setter
@Getter
public class Reservation {
	
	@Id 
	@Column(name="reservation_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservationId;
	
	//체크인날짜
	@Column
	private LocalDate checkin;
	
//	//체크아웃날짜
//	@Column
//	private LocalDate checkout;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="gh_id")
	private Guesthouse gh;

	@Override
	public String toString() {
		return "Reservation [reservationId=" + reservationId + ", checkin=" + checkin + "]";
	}
	
	public ReservationRes toReservationRes (Reservation r) {
		ReservationRes response = ReservationRes.builder()
				.checkin(r.getCheckin())
				.location(r.getGh().getLocation())
				.name(r.getGh().getName())
				.price(r.getGh().getPrice())
				.reservationId(r.getReservationId())
				.build();
		
		return response;
	}

}
