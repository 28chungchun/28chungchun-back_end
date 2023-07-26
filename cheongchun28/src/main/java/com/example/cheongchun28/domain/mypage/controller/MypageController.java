package com.example.cheongchun28.domain.mypage.controller;

import com.example.cheongchun28.domain.mypage.dto.MypageDto;
import com.example.cheongchun28.domain.mypage.service.MypageService;
import com.example.cheongchun28.global.common.dto.CustomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/mypage")
public class MypageController {
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

    private final MypageService mypageService;

    @PutMapping
    public CustomResponseDto changeMyInfo(@RequestBody MypageDto.ChangeMyInfoRequestDto requestDto, HttpServletRequest httpServletRequest) throws SQLException {
        return mypageService.changeMyInfo(requestDto, httpServletRequest);
    }

    @GetMapping
    public CustomResponseDto getMyInfo(HttpServletRequest httpServletRequest) {
        return mypageService.getMyInfo(httpServletRequest);
    }

    @PutMapping("/password")
    public CustomResponseDto changeMyPassword(@RequestBody MypageDto.ChangeMyPasswordRequestDto requestDto, HttpServletRequest httpServletRequest) {
        return mypageService.changeMyPassword(requestDto, httpServletRequest);
    }

    @GetMapping("/reservation")
    public CustomResponseDto getMyReservation(HttpServletRequest httpServletRequest) {
        return mypageService.getMyReservation(httpServletRequest);
    }
}