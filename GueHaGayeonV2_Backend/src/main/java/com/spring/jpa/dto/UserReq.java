package com.spring.jpa.dto;

import com.spring.jpa.entity.User;

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
public class UserReq {

	private Integer gender;
	private Integer age;
	private Integer height;
	private Integer weight;
	private String hobby;
	private String mbti;
	private Integer status;
	private Integer salary;
	private String id;
	private String password;
//	private Integer grade; //이걸 서비스 레이어 단에서 구현
	
	public User toUser(UserReq userReq, Integer grade) {
		return User.builder()
				.grade(grade)
				.id(id)
				.password(password)
				.gender(gender)
				.age(age)
				.height(height)
				.weight(weight)
				.mbti(mbti)
				.salary(salary)
				.status(status)
				.hobby(hobby)
				.build();
	}
	
	

}
