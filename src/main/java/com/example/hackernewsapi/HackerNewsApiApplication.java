package com.example.hackernewsapi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableCaching
@RestController
public class HackerNewsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackerNewsApiApplication.class, args);
	}

}
