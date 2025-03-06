package com.spring.jpa.service;

import org.springframework.stereotype.Service;

import com.spring.jpa.dto.UserReq;
import com.spring.jpa.entity.User;

@Service
public class ScoreService {
	public int calculateScore(UserReq user) {
		if (user == null) {
			throw new IllegalArgumentException("User 객체는 null일 수 없습니다.");
		}

		double score = 0;

		// Height score
		score += calculateHeightScore(user);

		// BMI score
		score += calculateBMIScore(user);

		// Age score
		score += calculateAgeScore(user);

		// Salary score
		score += calculateSalaryScore(user);

		// Marital status score
		score += calculateStatusScore(user);
		
		
		int finalScore = (int) score;
		int grade;
		
		if(finalScore > 55) {
			grade = 5;
		} else if(finalScore > 49) {
			grade = 4;
		} else if(finalScore > 39){
			 grade = 3;
		} else if(finalScore > 29) {
			grade = 2;
		} else {
			grade = 1;
		}
		
		return grade;
	}

	private double calculateHeightScore(UserReq user) {
		Integer height = user.getHeight();
		Integer gender = user.getGender();
		double baseScore = 10.0; // 황금 키에 해당하는 점수
		double idealHeight;

		if (gender == 1) { // Male
			idealHeight = 185.0; // 남자의 황금 키
		} else { // Female
			idealHeight = 165.0; // 여자의 황금 키
		}

		// 키 차이 계산
		double heightDiff = Math.abs(height - idealHeight);
		// 차이가 클수록 점수 감소, 최소 4점
		return Math.max(baseScore - heightDiff * 0.4, 0);
	}

	private double calculateBMIScore(UserReq user) {
	    Integer weight = user.getWeight();
	    Integer height = user.getHeight();

	    
	    // BMI 계산
	    double bmi = (weight / Math.pow(height / 100.0, 2));

	    // 이상적인 BMI 값과 범위 설정
	    double idealBMI = 20.0;
	    double baseScore = 10.0;

	    // 이상적인 BMI 범위 설정
	    double idealBMIUpper = 21.0;
	    double idealBMILower = 19.0;

	    // BMI가 이상적인 범위 내에 있을 경우
	    if (bmi >= idealBMILower && bmi <= idealBMIUpper) {
	        return baseScore; // 범위 내에서 점수는 그대로 유지
	    } else {
	        // 범위를 벗어날 경우 점수 급격히 감소
	        double bmiDiff = Math.max(Math.abs(bmi - idealBMILower), Math.abs(bmi - idealBMIUpper));
	        return Math.max(baseScore - bmiDiff * 1, 0); // 차이가 클수록 점수 급격히 감소
	    }
	}


	private double calculateAgeScore(UserReq user) {
	    Integer age = user.getAge();
	    Integer gender = user.getGender();

	    double baseScore = 10.0; // 황금 나이에 해당하는 점수
	    double idealAgeUpper;

	    // 남성, 여성에 따라 상한선 정의
	    if (gender == 1) { // Male
	        idealAgeUpper = 31.0; // 남자의 황금 나이 상한선
	    } else { // Female
	        idealAgeUpper = 29.0; // 여자의 황금 나이 상한선
	    }

	    // 나이가 상한선 이하이면 최대 점수 부여
	    if (age <= idealAgeUpper) {
	        return (int) baseScore;
	    } else {
	        // 나이가 상한선을 초과할 경우 점수 급격히 감소
	        double ageDiff = age - idealAgeUpper;
	        return (int) Math.max(baseScore - ageDiff * 1, 0); // 차이가 클수록 점수 급격히 감소
	    }
	}


	private double calculateSalaryScore(UserReq user) {
		Integer salary = user.getSalary();

		double baseScore = 20.0; // 최고 연봉 (1억 기준)
		double idealSalary = 10000; // 1억을 기준으로 설정 (만원 단위)

		// 연봉 차이를 계산 (만원 단위)
		double salaryDiff = Math.abs(salary - idealSalary);

		// 500만원 차이마다 1점씩 차감
		double scoreDecreaseRate = 500; // 500만원 차이마다 1점씩 차감

		// 연봉 차이를 바탕으로 점수 계산, 차이가 0이면 20점, 차이가 클수록 점수 감소
		double finalScore = Math.max(baseScore - (salaryDiff / scoreDecreaseRate), 0); // 최소 0점 유지

		return finalScore;
	}

	private int calculateStatusScore(UserReq user) {
		Integer status = user.getStatus();

		// 결혼 상태에 따른 점수 분배
		if (status == 0) { // 미혼
			return 15;
		} else if (status == 1) { // 돌싱
			return 10;
		} else { // 애 있는 돌싱
			return 0;
		}
	}
	
//	public static void main(String[] arg ) {
//		List<User> userList = new ArrayList<>();
//
//		userList.add(User.builder().gender(1).age(31).height(175).weight(68).salary(5800).status(2).build());
//
//		userList.add(User.builder().gender(1).age(28).height(182).weight(75).salary(7000).status(0).build());
//
//		userList.add(User.builder().gender(2).age(26).height(162).weight(55).salary(4500).status(1).build());
//
//		userList.add(User.builder().gender(1).age(35).height(188).weight(80).salary(9500).status(0).build());
//
//		userList.add(User.builder().gender(2).age(30).height(170).weight(60).salary(5500).status(2).build());
//
//		userList.add(User.builder().gender(1).age(40).height(170).weight(85).salary(8500).status(1).build());
//
//		userList.add(User.builder().gender(2).age(25).height(155).weight(50).salary(4000).status(0).build());
//
//		userList.add(User.builder().gender(1).age(22).height(176).weight(70).salary(6200).status(0).build());
//
//		userList.add(User.builder().gender(2).age(34).height(165).weight(63).salary(7200).status(1).build());
//
//		userList.add(User.builder().gender(1).age(27).height(180).weight(77).salary(8000).status(2).build());
//		
//		userList.add(User.builder().gender(1).age(30).height(175).weight(68).salary(4500).status(0).build());
//		
//		userList.add(User.builder().gender(1).age(34).height(173).weight(70).salary(4300).status(1).build());
//		
//		userList.add(User.builder().gender(1).age(36).height(160).weight(85).salary(4500).status(0).build());
//
//		// 출력하여 리스트 확인
//		ScoreService scoreService = new ScoreService();
//		for(User
//				user : userList)
//		{
//			System.out.println(scoreService.calculateScore(user));
//		}
//
//	}
}
