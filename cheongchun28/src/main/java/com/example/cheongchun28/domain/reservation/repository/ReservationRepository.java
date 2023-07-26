package com.example.cheongchun28.domain.reservation.repository;

import com.example.cheongchun28.domain.reservation.entity.Reservation;
import com.example.cheongchun28.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.room.className = :className AND r.status = 'CONFIRMED' AND (" +
            "(:startTime BETWEEN r.startTime AND r.endTime) OR " +
            "(:endTime BETWEEN r.startTime AND r.endTime) OR " +
            "(r.startTime BETWEEN :startTime AND :endTime))")
    List<Reservation> findOverlappingReservations(String className, LocalDateTime startTime, LocalDateTime endTime);

    List<Reservation> findByUserAndStartTimeBetweenAndEndTimeBetween(User user, LocalDateTime startTime1, LocalDateTime endTime1, LocalDateTime startTime2, LocalDateTime endTime2);

    Optional<Reservation> findByCode(String code);
}
