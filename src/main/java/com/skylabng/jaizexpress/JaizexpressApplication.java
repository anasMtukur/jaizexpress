package com.skylabng.jaizexpress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JaizexpressApplication {
	public static void main(String[] args) {

		SpringApplication.run(JaizexpressApplication.class, args);
	}

}
