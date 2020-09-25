package com.app;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan({"com.app", "com.controllers","com.services","com.repositories","com.models"})
@EnableMongoRepositories("com.repositories")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}
