package com.example.cheongchun28.domain.reservation.dto;


import com.example.cheongchun28.domain.reservation.entity.Reservation;
import com.example.cheongchun28.domain.reservation.entity.ReservationStatus;
import com.example.cheongchun28.domain.reservation.entity.Room;
import com.example.cheongchun28.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;


@NoArgsConstructor
public class ReservationRequestDto {


    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateReservationDto {

        private String className;

        @NotNull(message = "예약 시작 시간은 필수 입력 값입니다.")
        private LocalDateTime startTime;

        @NotNull(message = "예약 종료 시간은 필수 입력 값입니다.")
        private LocalDateTime endTime;

        private ReservationStatus status;

        private String topic;

        @Builder
        public CreateReservationDto(String className, LocalDateTime startTime, LocalDateTime endTime, String topic) {
            this.className = className;
            this.startTime = startTime;
            this.endTime = endTime;
            this.topic = topic;
        }

        public Reservation toEntity(Room room, User user) {
            return Reservation
                    .builder()
                    .user(user)
                    .startTime(startTime)
                    .endTime(endTime)
                    .room(room)
                    .status(ReservationStatus.CONFIRMED)
                    .topic(topic)
                    .build();
        }

        public boolean isMinimumHourInterval(LocalDateTime startTime, LocalDateTime endTime) {
            Duration duration = Duration.between(startTime, endTime);
            return duration.toHours() >= 1;
        }
    }
}
