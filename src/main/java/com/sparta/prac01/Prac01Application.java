package com.sparta.prac01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan // @WebServlet 어노테이션이 동작하게 함
@SpringBootApplication
public class Prac01Application {
	public static void main(String[] args) {
		SpringApplication.run(Prac01Application.class, args);
	}
}
