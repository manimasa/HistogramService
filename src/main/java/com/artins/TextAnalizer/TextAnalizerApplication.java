package com.artins.TextAnalizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.artins.TextAnalizer.entity.TextAnalizerEntity;
import com.artins.TextAnalizer.repository.TextAnalizerRepository;

@Import(SecurityAutoConfiguration.class)
@SpringBootApplication(scanBasePackages = { "com.artins.TextAnalizer" })
public class TextAnalizerApplication {

	private static final Logger log = LoggerFactory.getLogger(TextAnalizerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TextAnalizerApplication.class);
	}

	@Bean
	public CommandLineRunner demo(TextAnalizerRepository repository) {
		return (args) -> {
			repository.save(new TextAnalizerEntity(10, "Bauer"));
			repository.save(new TextAnalizerEntity(5, "O'Brian"));
			repository.save(new TextAnalizerEntity(0, "Bauer"));
			repository.save(new TextAnalizerEntity(2, "Palmer"));
			repository.save(new TextAnalizerEntity(10, "Palmer"));
			repository.save(new TextAnalizerEntity(15, "Palmer"));
			repository.save(new TextAnalizerEntity(17, "Palmer"));
			repository.save(new TextAnalizerEntity(15, "Palmer"));
			repository.save(new TextAnalizerEntity(40, "Palmer"));
			repository.save(new TextAnalizerEntity(10, "Dessler"));
			repository.save(new TextAnalizerEntity(6, "Dessler"));
			repository.save(new TextAnalizerEntity(17, "Bauer"));
			repository.save(new TextAnalizerEntity(9, "Bauer"));
			repository.save(new TextAnalizerEntity(15, "Dessler"));
			repository.save(new TextAnalizerEntity(40, "Frank"));
			repository.save(new TextAnalizerEntity(25, "Frank"));
			repository.save(new TextAnalizerEntity(6, "Jan"));
		};
	}

}
