package com.example.cheongchun28.domain.reservation.entity;


import com.example.cheongchun28.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "t_reservation_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ReservationMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_member_sequence_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_sequence_id")
    private Reservation reservation;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_sequence_id")
    private User user;


    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private ReservationMemberStatus status;


    @Column(name = "is_invitor")
    private String isInvitor; // 방장인지 아닌지



    @Builder
    public ReservationMember(Reservation reservation, User user) {
        this.reservation = reservation;
        this.user = user;
        this.status = ReservationMemberStatus.CONFIRMED;
    }
}
