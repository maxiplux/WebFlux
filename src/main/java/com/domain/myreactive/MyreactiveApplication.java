package com.domain.myreactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;


@SpringBootApplication
@EnableMongoAuditing
@EnableReactiveMongoRepositories
public class MyreactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyreactiveApplication.class, args);
    }
}
