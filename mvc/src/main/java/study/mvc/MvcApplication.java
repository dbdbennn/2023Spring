package study.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MvcApplication {
	public static void main(String[] args) {
		System.out.println("hello");
		SpringApplication.run(MvcApplication.class, args);
	}

}