package com.example.cheongchun28.domain.reservation.repository;

import com.example.cheongchun28.domain.reservation.entity.Reservation;
import com.example.cheongchun28.domain.reservation.entity.ReservationMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationMemberRepository extends JpaRepository<ReservationMember, Long> {

/*
    @Query(value = "SELECT * FROM t_reservation_member r WHERE r.reservation_Code = :reservationCode AND r.status In ('예약중')" , nativeQuery = true)
    ReservationMember findByReservationCode(@Param("reservationCode") String reservationCode);
*/

    @Query(value = " select * from t_reservation_member r where r.status = '참여중' group by r.user_sequence_Id", nativeQuery = true)
    List<ReservationMember> findByReservation(Reservation reservation);

 /*
//예약 참가 API 생성 후에 예약 조회할 때 쓰자.
    @Query("select r.user.userName" + " From t_reservation_member r " + " Where r.reservation_Code = :reservationCode" +
    " And r.status In ('예약중')")
    ReservationMember findMemberNameByReservationCode(@Param("reservationCode") String reservationCode);
    // 입력받은(클라이언트) 예약번호랑 예약 회원 테이블에 있는 예약정보안에 있는 예약번호랑 일치하고
    // 참가상태는 1과2인것 중 회원 id안에 있는 이름을 들고와라.=
 */

}
