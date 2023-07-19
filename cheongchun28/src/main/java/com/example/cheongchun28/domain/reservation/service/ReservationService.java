package com.example.cheongchun28.domain.reservation.service;

import com.example.cheongchun28.domain.reservation.dto.ReservationRequestDto;
import com.example.cheongchun28.domain.reservation.dto.ReservationResponseDto;
import com.example.cheongchun28.domain.reservation.entity.Reservation;
import com.example.cheongchun28.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReservationService { // 예약 서비스 로직

    private final ReservationRepository reservationRepository;

    public ReservationResponseDto createReservation(ReservationRequestDto.ReservationCreateRequestDto reservationCreateRequestDto) {

        Reservation reservation = new Reservation();

        // 필요한 속성
        reservation.setClassSequenceId(reservationCreateRequestDto.getClassSequenceId());
        reservation.setTopic(reservationCreateRequestDto.getTopic());
        reservation.setStartDate(reservationCreateRequestDto.getStartDate());
        reservation.setEndDate(reservationCreateRequestDto.getEndDate());

        reservationRepository.save(reservation); // 저장

        // 생성자를 이용하여 ReservationResponseDto 객체 생성
        //ReservationResponseDto reservationResponseDto = new ReservationResponseDto(reservation);
        return  new ReservationResponseDto(reservation);

    }

    //Reservation reservation = reservationRepository.findByReservationSequenceId(reservationCreateRequestDto); 이건 조회에서 쓰장
    private ReservationResponseDto convertToReservationResponseDto(Reservation createReservation) {

        //필요한 속성들
        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();
        reservationResponseDto.setClassSequenceId(createReservation.getClassSequenceId());
        reservationResponseDto.setTopic(createReservation.getTopic());
        reservationResponseDto.setStartDate(createReservation.getStartDate());
        reservationResponseDto.setEndDate(createReservation.getEndDate());

        return reservationResponseDto;
    }
}