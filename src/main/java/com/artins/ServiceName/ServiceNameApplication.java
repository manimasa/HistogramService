package com.artins.ServiceName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.artins.ServiceName.entity.ServiceNameEntity;
import com.artins.ServiceName.repository.ServiceNameRepository;

@Import(SecurityAutoConfiguration.class)
@SpringBootApplication(scanBasePackages = { "com.artins.ServiceName" })
public class ServiceNameApplication {

	private static final Logger log = LoggerFactory.getLogger(ServiceNameApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiceNameApplication.class);
	}

	@Bean
	public CommandLineRunner demo(ServiceNameRepository repository) {
		return (args) -> {
			repository.save(new ServiceNameEntity(10, "Bauer"));
			repository.save(new ServiceNameEntity(5, "O'Brian"));
			repository.save(new ServiceNameEntity(0, "Bauer"));
			repository.save(new ServiceNameEntity(2, "Palmer"));
			repository.save(new ServiceNameEntity(10, "Palmer"));
			repository.save(new ServiceNameEntity(15, "Palmer"));
			repository.save(new ServiceNameEntity(17, "Palmer"));
			repository.save(new ServiceNameEntity(15, "Palmer"));
			repository.save(new ServiceNameEntity(40, "Palmer"));
			repository.save(new ServiceNameEntity(10, "Dessler"));
			repository.save(new ServiceNameEntity(6, "Dessler"));
			repository.save(new ServiceNameEntity(17, "Bauer"));
			repository.save(new ServiceNameEntity(9, "Bauer"));
			repository.save(new ServiceNameEntity(15, "Dessler"));
			repository.save(new ServiceNameEntity(40, "Frank"));
			repository.save(new ServiceNameEntity(25, "Frank"));
			repository.save(new ServiceNameEntity(6, "Jan"));
		};
	}

}
