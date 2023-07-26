package com.example.cheongchun28.domain.reservation.entity;


import com.example.cheongchun28.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
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
    private ReservationMemberStatus status;

}
