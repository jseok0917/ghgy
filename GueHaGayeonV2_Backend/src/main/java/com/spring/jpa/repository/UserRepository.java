package com.spring.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.jpa.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	//아이디로 유저 찾기
	@Query("select u from User u where u.id = :id")
	Optional<User> getUser(String id);

}
