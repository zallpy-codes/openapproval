package com.zallpy.openapproval;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class OpenapprovalApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenapprovalApplication.class, args);
	}

}
