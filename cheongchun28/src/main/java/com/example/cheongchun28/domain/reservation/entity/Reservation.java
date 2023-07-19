package com.example.cheongchun28.domain.reservation.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) //이를 통해 엔터티의 생성일(created_at) 및 수정일(updated_at) 등의 필드 값을 자동으로 설정할 수 있다.
@Data
@Entity
@Table(name = "T_RESERVATION")
public class Reservation { // 예약 엔티티 = 예약 테이블 컬럼들
    // 엔티티는 되도록이면 setter는 안쓰는게 좋다.
    // 한사람당 한 예약만 가능하도록.
    // 회원과 예약 1:N 관계, 강의실과 예약 1:1 관게

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본키값 자동으로 생성
    @Column(name = "RESERVATION_SEQUENCE_ID", nullable = false)
    private long reservationSequenceId; // 예약 고유 ID

    //    @OneToOne
//    @JoinColumn(name = "CLASS_SEQUENCE_ID", nullable = false)
    @Column(name = "CLASS_SEQUENCE_ID", nullable = false)
    private int classSequenceId; // 강의실 ID
    //필드를 객체 타입으로 변경하는 것이 좋다..

    //    @ManyToOne
//    @JoinColumn(name = "USER_SEQUNENCE_ID", nullable = false)
    @Column(name = "USER_SEQUENCE_ID", nullable = false)
    private long userSequenceId; // 예약한 사용자 ID

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt; // 예약 생성 일자

    @LastModifiedDate
    @Column(name = "MODIFIED_AT", nullable = false)
    private LocalDateTime modifiedAt; // 예약 수정 일자

    @Column(name = "start_Date", nullable = false)
    private LocalDateTime startDate; // 예약 시작 시간

    @Column(name = "end_Date", nullable = false)
    private LocalDateTime endDate; // 예약 종료 시간

    @Column(name = "status", nullable = false)
    private String status; // 예약 상태

    @Column(name = "topic", nullable = false)
    private String topic; // 예약 목적
/*
    @Column(name = "userName", nullable = false)
    private String userName;
*/
   /* public Reservation(long reservationSequenceId, int classSequenceId, long userSequenceId, String topic, String userName,
                       LocalDateTime startTime, LocalDateTime endTime, String particName, String particProfile, int reservationState) {
        this.reservationSequenceId = reservationSequenceId,
                this.classSequenceId = classSequenceId,
                this.userSequenceId = userSequenceId,
                this.topic = topic,
                this.userName = userName,
                this.startTime = startTime,
                this.endTime = endTime,
                this.particName
    }*/


}
