package com.bnksystem.trainning1team;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//public class Trainning1teamApplication {
public class Trainning1teamApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Trainning1teamApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Trainning1teamApplication.class, args);
	}

}
