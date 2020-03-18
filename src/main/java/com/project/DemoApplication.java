package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.project.model"})
@ComponentScan({"com.project.repository"})
@ComponentScan({"com.project.service"})
@ComponentScan({"com.project.controller"})
@ComponentScan({"com.project.exception"})
@ComponentScan({"com.project.validation"})
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
