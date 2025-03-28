package com.sibsutis.study.SpringBootWithDB;

import org.springframework.boot.SpringApplication;

public class TestSpringBootWithDbApplication {

	public static void main(String[] args) {
		SpringApplication.from(Testwork3Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
