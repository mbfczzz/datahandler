package com.datahandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DateHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DateHandlerApplication.class, args);
	}

}
