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
    @Enumerated(EnumType.ORDINAL)
    private ReservationMemberStatus status;


    @Column(name = "IS_INVITOR")
    private String isInvitor; // 방장인지 아닌지



    @Builder
    public ReservationMember(Reservation reservation, User user) {
        this.reservation = reservation;
        this.user = user;
        this.status = ReservationMemberStatus.CONFIRMED;
    }

    public void cancelReservationMember(){
        this.status = ReservationMemberStatus.CANCELLED;
    }
}
