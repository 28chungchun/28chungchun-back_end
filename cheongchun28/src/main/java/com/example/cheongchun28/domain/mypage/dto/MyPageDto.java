package com.example.cheongchun28.domain.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MyPageDto {

    /*
    * 회원 정보 변경 put /api/mypage
        "username": "changeExampleUsername"
        “profileImage”: “exampleImageURL.com”,
        ”notificationAgreement”: true(false)
    * 회원 정보 조회 get /api/mypage
    * 비밀번호 변경 put /api/mypage/password
        ”password”: “examplePassword”,
        ”newPassword”: “newExamplePassword”
    * 예약 기록 조회 get /api/mypage/reservation*/
    @Getter
    public static class ChangeMyInfoRequestDto {
        private String nickName;
        private String profileImage;
        private boolean notificationAgreement;
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


    public static class getMyReservationResponseDto {
    }
}
