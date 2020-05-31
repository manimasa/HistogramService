package com.dols.HistogramService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;


@Import(SecurityAutoConfiguration.class)
@SpringBootApplication(scanBasePackages = { "com.dols.HistogramService" })
public class HistogramServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HistogramServiceApplication.class);
	}
}
