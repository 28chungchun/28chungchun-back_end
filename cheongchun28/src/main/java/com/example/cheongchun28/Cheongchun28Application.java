package com.example.cheongchun28;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Cheongchun28Application {

    public static void main(String[] args) {
        SpringApplication.run(Cheongchun28Application.class, args);
    }

}
