package com.example.cheongchun28.domain.reservation.repository;

import com.example.cheongchun28.domain.reservation.dto.ReservationRequestDto;
import com.example.cheongchun28.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 예약 조회 레파지토리
    Reservation findByUserSequenceId(ReservationRequestDto.ReservationGetRequestDto reservationCreateRequestDto);

    // 예약 수정 레파지토리
    Reservation findByUserSequenceId(ReservationRequestDto.ReservationUpdateRequestDto reservationUpdateRequestDto);

    // 예약 삭제 레파지토리
    Reservation deleteByUserSequenceId(ReservationRequestDto.ReservationDeleteRequestDto reservationDeleteRequestDto);

    Reservation deleteByUserSequenceId(int userSequenceId);

}
