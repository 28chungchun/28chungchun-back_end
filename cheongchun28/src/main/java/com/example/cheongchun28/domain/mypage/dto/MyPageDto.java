package com.example.cheongchun28.domain.mypage.dto;

import com.example.cheongchun28.domain.reservation.entity.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class MyPageDto {
    @Getter
    public static class ChangeMyInfoRequestDto {
        private String nickName;
        private String profileImage;
        private boolean notificationAgreement;
        private String empNumber;
    }

    @Getter
    public static class ChangeMyPasswordRequestDto {
        private String password;
        private String newPassword;
    }

    @AllArgsConstructor
    @Getter
    public static class getMyInfoResponseDto {
        private String email;
        private String nickName;
        private String profileImage;
        private String empNumber;
    }


    @Setter
    @Getter
    public static class getMyReservationResponseDto {
        private String roomName;
        private String nickName;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private ReservationStatus status;
        private String ReservationCode;
    }
}
