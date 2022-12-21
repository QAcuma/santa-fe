package ru.acuma.santafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SantaFeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SantaFeApplication.class, args);
    }

}
