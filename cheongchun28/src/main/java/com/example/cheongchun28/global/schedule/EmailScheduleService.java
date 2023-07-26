package com.example.cheongchun28.global.schedule;

import com.example.cheongchun28.global.email.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public
class EmailScheduleService {

    private final EmailRepository emailRepository;

    @Scheduled(cron = "0 0 12 * * ?")
    @Transactional
    public void emailconfirmcodeDelete() {
        log.info("스케줄러 실행됨");
        LocalDateTime afterLimitTime = LocalDateTime.now().minusMinutes(3);

        emailRepository.deleteOlderThanlimitTime(afterLimitTime);
        log.info("현재 시간 기준 3분전보다 지난 데이터를 삭제함");
    }
}
