package com.example.cheongchun28.domain.reservation.entity;

import com.example.cheongchun28.domain.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "T_RESERVATION_MEMBER")
public class ReservationMember {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본키값 자동으로 생성
    @Column(name = "RESERVATION_MEMBER_ID", nullable = false)
    private long reservationMemberId; // 예약회원 고유 번호

    @OneToMany
    @JoinColumn(name = "RESERVATION_SEQUENCE_ID", nullable = false)
    private List<Reservation> reservation; // 예약 고유 ID

    @OneToMany
    @JoinColumn(name = "USER_SEQUENCE_ID", nullable = false)
    private List<User> user; // 참가한 사용자 ID

    @Column(name = "STATUS", nullable = false)
    private String status; // 예약 참가 상태

}
