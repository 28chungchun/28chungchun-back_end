package com.example.cheongchun28.domain.reservation.dto;

import com.example.cheongchun28.domain.reservation.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReservationResponseDto {

    private  int classSequenceId;
    private  String topic;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    // 생성자를 이용하여 통합
    public ReservationResponseDto(Reservation reservation) {
        this.classSequenceId = reservation.getClassSequenceId();
        this.topic = reservation.getTopic();
        this.startDate = reservation.getStartDate();
        this.endDate = reservation.getEndDate();
    }
    @Getter
    @Setter
    public static class ReservationCreateResponseDto { // 예약 생성에 대한 응답값

        private int classSequenceId; // 강의실ID
        private boolean success;
    }
}
