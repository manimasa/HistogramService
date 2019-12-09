package com.doubleverify.HstService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;


@Import(SecurityAutoConfiguration.class)
@SpringBootApplication(scanBasePackages = { "com.doubleverify.HstService" })
public class HstServiceApplication {	
	
	public static void main(String[] args) {
		SpringApplication.run(HstServiceApplication.class);
	}
}
