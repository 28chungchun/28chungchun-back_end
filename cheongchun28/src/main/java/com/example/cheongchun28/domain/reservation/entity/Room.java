package com.example.cheongchun28.domain.reservation.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "T_class")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_sequence_id")
    private Long id;

    @Column(name = "classname")
    private String className;

    @Column(name = "bimproject")
    private boolean bimProject;

    @Column(name = "computer")
    private boolean computer;
    @Column(name = "blackboard")
    private boolean blackBoard;

    @Column(name = "capacity")
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
