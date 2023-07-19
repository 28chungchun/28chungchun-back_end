package com.example.cheongchun28.domain.reservation.controller;

import com.example.cheongchun28.domain.reservation.dto.ReservationRequestDto;
import com.example.cheongchun28.domain.reservation.dto.ReservationResponseDto;
import com.example.cheongchun28.domain.reservation.entity.Reservation;
import com.example.cheongchun28.domain.reservation.service.ReservationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor // 생성자 자동으로 생성
@RequestMapping("/api/reservation")
@RestController // 어노테이션으로 해당 Class가 Controller임을 알려준다.
@Getter
@Setter
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    //예약하기
    public ReservationResponseDto createReservation(@RequestBody ReservationRequestDto.ReservationCreateRequestDto reservationCreateRequestDto){

        return reservationService.createReservation(reservationCreateRequestDto);
        // 클래스 Resevation의 변수 createReservation에 reservationService.createReservation(reservationCreateRequestDto) 메소드 호출값을 대입시킨다.
    }
    // ReservationResponseDto 타입으로 createReservation 메소드를 선언한다..
    // 클래스 Resevation의 변수 createReservation에 reservationService.createReservation(reservationCreateRequestDto) 메소드 호출값을 대입시킨다.
    // 대입된 변수 createReservation를 convertToReservationDto()메소드 안에 담아서, 예약 정보를 ReservationResponseDto 객체에 맞게 변환하여 반환한다.
    // ReservationResponseDto를 리턴한다.

}
