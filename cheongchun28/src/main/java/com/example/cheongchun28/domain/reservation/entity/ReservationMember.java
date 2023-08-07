package com.example.cheongchun28.domain.reservation.entity;


import com.example.cheongchun28.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "T_RESERVATION_MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ReservationMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_MEMBER_SEQUENCE_ID")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESERVATION_SEQUENCE_ID")
    private Reservation reservation;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQUENCE_ID")
    private User user;


    @Column(name = "STATUS")
    private boolean status; // 참가 상태


    @Column(name = "IS_INVITOR")
    private boolean isInvitor; // 방장인지 아닌지

    @Column(name = "ATTENDANCE")
    private boolean attendance; // 출결 상태

    @Builder
    public ReservationMember(Reservation reservation, User user) {
        this.reservation = reservation;
        this.user = user;
        this.status = false;
        this.attendance = false;
    }

    public void cancelReservationMember(){
        this.status = true;
    }

    public void checkOutReservationMember() { this.attendance = true; }
}
