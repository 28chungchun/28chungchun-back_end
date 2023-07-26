package com.example.cheongchun28.domain.reservation.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data
@Table(name = "T_Room")
public class Room { // 강의실

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본키값 자동으로 생성
    @Column(name = "ROOM_SEQUENCE_ID", nullable = false)
    private long roomSequenceId;

    @Column(name = "ROOM_NAME", nullable = false)
    private String roomName;

    @Column(name = "BEAM_PROJECT", nullable = false)
    private boolean beamProject;

    @Column(name = "COMPUTER", nullable = false)
    private boolean computer;

    @Column(name = "BLACKBOARD", nullable = false)
    private boolean blackBoard;

    @Column(name = "CAPACITY", nullable = false)
    private int capacity;

}
