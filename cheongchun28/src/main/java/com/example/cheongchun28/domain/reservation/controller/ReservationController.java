package com.example.cheongchun28.domain.reservation.controller;

import com.example.cheongchun28.domain.reservation.dto.ReservationRequestDto;
import com.example.cheongchun28.domain.reservation.dto.ReservationResponseDto;
import com.example.cheongchun28.domain.reservation.service.ReservationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor // 생성자 자동으로 생성
//@RequestMapping("/api/reservation")
@RestController // 어노테이션으로 해당 Class가 Controller임을 알려준다.
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/api/reservation")
    //예약하기(Create/Post)
    public ReservationResponseDto.ReservationCreateResponseDto createdReservation(@RequestBody ReservationRequestDto.ReservationCreateRequestDto reservationCreateRequestDto){
        log.info("reservationRequestDto : {}", reservationCreateRequestDto);
        return reservationService.createReservation(reservationCreateRequestDto);

    }

    @GetMapping("/{userSequenceId}")
    //조회하기(Read/Get)
    public ReservationResponseDto.ReservationGetResponseDto getedReservation(@RequestBody ReservationRequestDto.ReservationGetRequestDto reservationGetRequestDto){
        return reservationService.getReservation(reservationGetRequestDto);
    }

    @PutMapping("/{userSequenceId}")
    //수정하기(Update/put)
    public ReservationResponseDto.ReservationUpdateResponseDto updatedReservation(@PathVariable Long userSequenceId, @RequestBody ReservationRequestDto.ReservationUpdateRequestDto reservationUpdateRequestDto) {

        // 클라이언트가 전달한 requestDto를 서비스 클래스로 전달하여 예약 정보 업데이트를 수행하고, 업데이트된 예약 정보를 그대로 반환한다.
        return reservationService.updateReservation(userSequenceId, reservationUpdateRequestDto);

       // return reservationService.updateReservation(reservationUpdateRequestDto);
    }

    @DeleteMapping("/{userSequenceId}")
    //삭제하기(Delete)
    public ReservationResponseDto.ReservationDeleteResponseDto deleteddReservation(@PathVariable Long userSequenceId, @RequestBody ReservationRequestDto.ReservationDeleteRequestDto reservationDeleteRequestDto){
        return reservationService.deleteReservation(userSequenceId, reservationDeleteRequestDto);
    }
}
