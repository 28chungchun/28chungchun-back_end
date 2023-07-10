package com.example.cheongchun28.domain.reservation.service;

import com.example.cheongchun28.domain.reservation.dto.ReservationDto;
import com.example.cheongchun28.domain.reservation.entity.ReservationEntity;
import com.example.cheongchun28.domain.reservation.repository.ReservationRepository;
import com.example.cheongchun28.global.common.dto.CustomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public CustomResponseDto createReservation(ReservationDto.CreateRequestDto requestDto) {
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationRepository.save(reservationEntity);
        return new CustomResponseDto(200);
    }

    public CustomResponseDto getReservation(Long reservationId) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("Reservation not found"));
        ReservationDto.ResponseDto responseDto = new ReservationDto.ResponseDto();
        return new CustomResponseDto(200);
    }

    public CustomResponseDto statusReservation(Long reservationId) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("Reservation not found"));
        ReservationDto.ResponseDto responseDto = new ReservationDto.ResponseDto();
        return new CustomResponseDto(200);
    }

    public CustomResponseDto updateReservation(Long reservationId, ReservationDto.UpdateRequestDto requestDto) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservationRepository.save(reservationEntity);
        return new CustomResponseDto(200);
    }

    public CustomResponseDto deleteReservation(Long reservationId) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservationRepository.delete(reservationEntity);
        return new CustomResponseDto(200);
    }
}