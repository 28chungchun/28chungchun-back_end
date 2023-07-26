package com.example.cheongchun28.global.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ScheduleConfig {

    @Bean
    public ThreadPoolTaskScheduler schedulerExecutor() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(4);
        taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return taskScheduler;
    }
}
