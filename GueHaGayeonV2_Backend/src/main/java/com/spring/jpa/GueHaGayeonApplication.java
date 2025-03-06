package com.spring.jpa;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.jpa.controller.ReservationController;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class GueHaGayeonApplication {
	
	@Autowired
	private ReservationController rController;
	
	static int numThreads = 100;

	public static void main(String[] args) {
		SpringApplication.run(GueHaGayeonApplication.class, args);
	}
	
	

//	@Transactional
//	@Override
//	public void run(String... args) throws Exception {
//
//        CountDownLatch latch = new CountDownLatch(numThreads);
//        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        for (int i = 1; i < 20; i++) {
//        	int j = i+1;
//            executorService.submit(() -> {
//                try {
//                    rController.registerReservation(LocalDate.parse("2025-03-12"), j, 13);
//                } catch (Exception e) {
//                }
//            });
//        }
//
//        
//        // 모든 스레드가 완료될 때까지 대기
//        latch.await();
//        executorService.shutdown();
//	
//	}


}
