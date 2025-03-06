package com.spring.jpa.entity;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
	//아이디, 비밀번호 필드 추가
	//회원가입 추가
	//예약 취소 기능 추가
	
	
	@Id //PK
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	//로그인용 유저id임
	private String id;
	private String password;
	
	//1 남자
	//2 여자
	@Column(name="gender")
	private Integer gender;
	
	@Column(name="age")
	private Integer age;
	
	//단위 cm
	@Column(name="height")
	private Integer height;
	
	//단위 kg
	@Column(name="weight")
	private Integer weight;
	
	
	@Column(name="hobby")
	private String hobby;
	
	@Column(name="mbti")
	private String mbti;
	
	//0미혼 1기혼 2돌싱
	@Column(name="status")
	private Integer status;
	
	//단위 만
	@Column(name="salary")
	private Integer salary;
	
	//키, 몸무게, 나이, 연봉, 기혼여부 5가지로 score 계산해서 grade 계산
	@Column(name="grade")
	private Integer grade;
	
	//유저 이미지 경로
	private String userImgURL;

	@Override
	public String toString() {
		return "User [userId=" + userId + ", gender=" + gender + ", age=" + age + ", height=" + height + ", weight="
				+ weight + ", hobby=" + hobby + ", mbti=" + mbti + ", status=" + status + ", salary=" + salary
				+ ", grade=" + grade + "]";
	}
	
	
	
	
}
