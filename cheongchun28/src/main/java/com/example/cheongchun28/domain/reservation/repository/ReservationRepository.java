package com.example.cheongchun28.domain.reservation.repository;

import com.example.cheongchun28.domain.reservation.entity.Reservation;
import com.example.cheongchun28.domain.reservation.entity.ReservationStatus;
import com.example.cheongchun28.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.room.roomName = :className AND r.status = 'CONFIRMED' AND (" +
            "(:startDate BETWEEN r.startDate AND r.endDate) OR " +
            "(:endDate BETWEEN r.startDate AND r.endDate) OR " +
            "(r.startDate BETWEEN :startDate AND :endDate))")
    List<Reservation> findOverlappingReservations(@Param("className") String className, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT r FROM Reservation r WHERE r.user.userEmail = :userId AND r.status IN :statuses")
    List<Reservation> findByUserAndStatusIn(@Param("userId") Long userId, @Param("statuses") List<ReservationStatus> statuses);
//    List<Reservation> findByUserAndStartDateBetweenAndEndDateBetweenAndStatusNot(user, startDate, endDate, startDate, endDate, ReservationStatus.CANCELLED User user, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime date, LocalDateTime localDateTime, ReservationStatus cancelled);
//
//    List<Reservation> findByRoomAndStartDateBetweenAndEndDateBetweenAndStatusNot(room, startDate, endDate, startDate, endDate, ReservationStatus.CANCELLED Room room, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime date, LocalDateTime localDateTime, ReservationStatus cancelled);

    List<Reservation> findByUserAndStartDateBetweenAndEndDateBetween(User user, LocalDateTime startDate1, LocalDateTime endDate1, LocalDateTime startDate2, LocalDateTime endDate2);

    Optional<Reservation> findByCode(String code);


    // 상태가 예약중인 것과 이메일이 일치하는 것만 가져온다.
    @Query("SELECT r FROM Reservation r WHERE r.status = '0' AND r.user.userEmail = :email")
    Reservation findWaitingReservationByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM t_reservation r WHERE r.reservation_Code = :code AND r.status In ('0')" , nativeQuery = true)
    Reservation findByReservationCode(@Param("code") String code);

    List<Reservation> findAllByUser(User user);

    @Query(value = "SELECT * FROM t_reservation r WHERE r.status != 'CANCELLED' AND (r.start_date < ?2 AND r.end_date > ?1)", nativeQuery = true)
    List<Reservation> findOverlappingReservationsInDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
