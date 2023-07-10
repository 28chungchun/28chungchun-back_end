package com.example.cheongchun28.domain.reservation.controller;

import com.example.cheongchun28.domain.reservation.dto.ReservationDto;
import com.example.cheongchun28.domain.reservation.service.ReservationService;
import com.example.cheongchun28.global.common.dto.CustomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/api/reservation/create")   // create 빼버릴까?
    public CustomResponseDto createReservation(@RequestBody ReservationDto.CreateRequestDto requestDto) {
        log.info("예약하러 가기");
        return reservationService.createReservation(requestDto);
    }

    @GetMapping("/api/reservation/{reservationId}")
    public CustomResponseDto getReservation(@PathVariable("reservationId") Long reservationId) {
        log.info("내 예약현황 조회하기");
        return reservationService.getReservation(reservationId);
    }

    @GetMapping("/api/reservation/status")
    public CustomResponseDto statusReservation(@PathVariable("classId") Long classId) {
        log.info("강의실 예약상태 조회하기");
        return reservationService.statusReservation(classId);
    }

    @PostMapping("/api/reservation/{reservationId}/update")
    public CustomResponseDto updateReservation(@PathVariable("reservationId") Long reservationId, @RequestBody ReservationDto.UpdateRequestDto requestDto) {
        log.info("내 예약 수정하기");
        return reservationService.updateReservation(reservationId, requestDto);
    }

    @PostMapping("/api/reservation/delete")
    public CustomResponseDto deleteReservation(@PathVariable("reservationId") Long reservationId) {
        log.info("내 예약 취소하기");
        return reservationService.deleteReservation(reservationId);
    }
}

// @PathVariable = 다양한 경로라는 뜻 / RESTful 웹의 URL 경로에서 동적 값을 처리하는 데 사용됨
// URl에서 경로변수값을 가져와서 컨트롤러의 메서드의 매개변수로 사용할 수 있음
// 경로 변수는 RESTful API에서 동적인 값을 포함하는 경로의 일부를 나타내는데 사용됨 예를들면 위에서는 reservationId가 경로변수, ERD클라우드에는 예약테이블 PK. RESERVATION_SEQUENCE_ID로 되어있음


// 예시로 @PathVariable 어노테이션으로 해당 {reservationId}(경로 변수) 값을 메서드 매개변수에 묶음(바인딩).
// 이 묶인 매개변수는 앞으로 getReservation나 statusReservation과 같은 메서드의 매개변수로 요긴하게 사용될거임.
// 이처럼 @PathVariable는 URL 경로에서 다양한 값을 처리할 수 있는 동적이고 유연한 API를 생성할 수 있음

// 클라이언트님. 님이 입력한 경로 변수 이제 내 메서드 매개변수임. 님 변수 쩔더라.같은 느낌?