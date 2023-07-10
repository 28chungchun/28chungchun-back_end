package com.example.cheongchun28.domain.reservation.dto;

import com.example.cheongchun28.domain.user.dto.UserDto;
import com.example.cheongchun28.domain.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
public class ReservationDto {

    @Getter
    public static class CreateRequestDto {

        @NotBlank(message = "예약자 ID는 필수 입력값입니다.")
        private UserEntity userSequenceId;

        @NotNull(message = "예약 시작 일시는 필수 입력값입니다.")
        @FutureOrPresent(message = "예약 시작 일시는 미래 시간을 입력하셔야 합니다.")
        private LocalDateTime startTime;

        @NotNull(message = "예약 종료 일시는 필수 입력값입니다.")
        @FutureOrPresent(message = "예약 종료 일시는 미래 시간을 입력하셔야 합니다..")
        private LocalDateTime endTime;

        @NotBlank(message = "예약 목적은 필수 입력값입니다.")
        private String purpose;

        @NotBlank(message = "강의실 호수는 필수 입력값입니다.")
        private Long classSequenceID;
        
        
        private UserDto.loginRequestDto requester;

    }

    @Getter // 수정할 수도, 안할수도 있으니 낫블랭크와 낫널은 생략합니다.
    public static class UpdateRequestDto {

        private UserEntity userSequenceId;

        private LocalDateTime startTime;

        private LocalDateTime endTime;

        private String purpose;

        private Long classSequenceID;

    }

    @Getter
    public static class ResponseDto {

        private Long reservationId;

        private UserEntity userSequenceId;

        private LocalDateTime startTime;

        private LocalDateTime endTime;

        private String purpose;

        private Long classSequenceID;

        private UserDto.loginRequestDto requester;

    }
}