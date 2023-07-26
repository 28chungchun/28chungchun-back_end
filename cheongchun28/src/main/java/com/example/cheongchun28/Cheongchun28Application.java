package com.example.cheongchun28;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling //스케줄링 기능을 enable함.
public class Cheongchun28Application {

    public static void main(String[] args) {
        SpringApplication.run(Cheongchun28Application.class, args);
    }

}
