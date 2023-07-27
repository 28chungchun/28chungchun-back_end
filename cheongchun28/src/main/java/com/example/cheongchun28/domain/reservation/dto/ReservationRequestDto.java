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

    public static boolean isMinimumHourInterval(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.toHours() >= 1;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateReservationDto {

        private String className;

        @NotNull(message = "예약 시작 시간은 필수 입력 값입니다.")
        private LocalDateTime startDate;

        @NotNull(message = "예약 종료 시간은 필수 입력 값입니다.")
        private LocalDateTime endDate;

        private ReservationStatus status;

        private String topic;

        @Builder
        public CreateReservationDto(String className, LocalDateTime startDate, LocalDateTime endDate, String topic) {
            this.className = className;
            this.startDate = startDate;
            this.endDate = endDate;
            this.topic = topic;
        }

        public Reservation toEntity(Room room, User user) {
            return Reservation
                    .builder()
                    .user(user)
                    .startDate(startDate)
                    .endDate(endDate)
                    .room(room)
                    .status(ReservationStatus.CONFIRMED)
                    .topic(topic)
                    .build();
        }

        public boolean isValid() {
            return ReservationRequestDto.isMinimumHourInterval(startDate, endDate);
        }
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateReservationDto {

        @NotNull(message = "예약 시작 시간은 필수 입력 값입니다.")
        private LocalDateTime startDate;

        @NotNull(message = "예약 종료 시간은 필수 입력 값입니다.")
        private LocalDateTime endDate;

        @NotNull(message = "예약 사유를 입력해주세요.")
        private String topic;

        public UpdateReservationDto(LocalDateTime startDate, LocalDateTime endDate, String topic) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.topic = topic;
        }

        public boolean isValid() {
            return ReservationRequestDto.isMinimumHourInterval(startDate, endDate);
        }

    }
}
