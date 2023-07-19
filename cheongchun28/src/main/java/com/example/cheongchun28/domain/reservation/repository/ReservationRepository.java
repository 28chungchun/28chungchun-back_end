package com.example.cheongchun28.domain.reservation.repository;

import com.example.cheongchun28.domain.reservation.dto.ReservationRequestDto;
import com.example.cheongchun28.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findByReservationSequenceId(ReservationRequestDto.ReservationCreateRequestDto reservationCreateRequestDto);
}
