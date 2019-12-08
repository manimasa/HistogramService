package com.artins.TextAnalizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;


@Import(SecurityAutoConfiguration.class)
@SpringBootApplication(scanBasePackages = { "com.artins.TextAnalizer" })
public class TextAnalizerApplication {

	private static final Logger log = LoggerFactory.getLogger(TextAnalizerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TextAnalizerApplication.class);
	}
}
