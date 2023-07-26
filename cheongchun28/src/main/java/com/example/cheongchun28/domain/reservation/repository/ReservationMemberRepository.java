package com.example.cheongchun28.domain.reservation.repository;

import com.example.cheongchun28.domain.reservation.entity.Reservation;
import com.example.cheongchun28.domain.reservation.entity.ReservationMember;
import com.example.cheongchun28.domain.reservation.entity.ReservationMemberStatus;
import com.example.cheongchun28.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ReservationMemberRepository extends JpaRepository<ReservationMember, Long> {

//    List<ReservationMember> findByUserAndStatus(User user, ReservationMemberStatus status);

    ReservationMember findByReservationAndUser(Reservation reservation, User user);
}