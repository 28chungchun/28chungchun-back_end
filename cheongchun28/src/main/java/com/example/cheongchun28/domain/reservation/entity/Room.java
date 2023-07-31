package com.example.cheongchun28.domain.reservation.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "T_CLASS")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLASS_SEQUENCE_ID")
    private Long id;

    @Column(name = "CLASSNAME")
    private String className;

    @Column(name = "BIMPROJECT")
    private boolean bimProject;

    @Column(name = "COMPUTER")
    private boolean computer;

    @Column(name = "BLACKBOARD")
    private boolean blackBoard;

    @Column(name = "CAPACITY")
    private int capacity;

    @JsonIgnore
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private final List<Reservation> reservations = new ArrayList<>();


    public boolean isRoomReserved(LocalDateTime startTime, LocalDateTime endTime) {
        for (Reservation reservation : reservations) {
            LocalDateTime reservationStartTime = reservation.getStartDate();
            LocalDateTime reservationEndTime = reservation.getEndDate();
            if (reservationStartTime.isBefore(endTime) && reservationEndTime.isAfter(startTime)) {
                return true;
            }
        }
        return false;
    }

}
