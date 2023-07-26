package com.example.cheongchun28.domain.reservation.controller;

import com.example.cheongchun28.domain.reservation.dto.ReservationRequestDto;
import com.example.cheongchun28.domain.reservation.dto.ReservationResponseDto;
import com.example.cheongchun28.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@RequiredArgsConstructor // 생성자 자동으로 생성
@RequestMapping("/api/reservation")
@RestController // 어노테이션으로 해당 Class가 Controller임을 알려준다.
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping()
    //예약하기(Create/Post)
    public ReservationResponseDto.ReservationCreateResponseDto createdReservation(@RequestBody ReservationRequestDto.ReservationCreateRequestDto reservationCreateRequestDto, HttpServletRequest httpServletRequest) throws SQLException {
        log.info("reservationRequestDto : {}", reservationCreateRequestDto);
        return reservationService.createReservation(reservationCreateRequestDto, httpServletRequest);
    }

    @GetMapping()
    //조회하기(Read/Get) - (전체)
    public ReservationResponseDto.ReservationGetResponseDto getedReservation(HttpServletRequest httpServletRequest) {
        return reservationService.getReservation(httpServletRequest);
    }

    @GetMapping("/{reservationCode}")
    //예약 건당 조회하기(Read/Get) - (하나에 대한)
    public ReservationResponseDto.ReservationGetOneResponseDto getedReservation(@PathVariable String reservationCode) {
        return reservationService.getReservation(reservationCode);
    }

    @PutMapping("/{reservationCode}")
    //수정하기(Update/put) - (하나에 대한)
    public ReservationResponseDto.ReservationUpdateResponseDto updatedReservation(@PathVariable String reservationCode, @RequestBody ReservationRequestDto.ReservationUpdateRequestDto reservationUpdateRequestDto) {
        return reservationService.updateReservation(reservationCode, reservationUpdateRequestDto);
    }

    @DeleteMapping("/{reservationCode}")
    //삭제하기(Delete)  - 예약 상태를 "예약중" -> "예약 취소" 로 변경하는 부분
    // 음 예약 취소하기를 누르면 상태가 변하게끔
    public ReservationResponseDto.ReservationDeleteResponseDto deletedReservation(@PathVariable String email, @RequestBody ReservationRequestDto.ReservationDeleteRequestDto reservationDeleteRequestDto){
        return reservationService.deleteReservation(email, reservationDeleteRequestDto);
    }
}
