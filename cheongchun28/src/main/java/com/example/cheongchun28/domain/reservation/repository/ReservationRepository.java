package com.example.cheongchun28.domain.reservation.repository;

import com.example.cheongchun28.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 상태가 예약중인 것과 이메일이 일치하는 것만 가져온다.
    @Query("SELECT r FROM Reservation r WHERE r.status = '예약중' AND r.user.userEmail = :userEmail")
    Reservation findWaitingReservationByEmail(@Param("userEmail") String userEmail);

    @Query(value = "SELECT * FROM t_reservation r WHERE r.reservation_Code = :reservationCode AND r.status In ('예약중')" , nativeQuery = true)
    Reservation findByReservationCode(@Param("reservationCode") String reservationCode);

}
