package com.example.cheongchun28.domain.reservation.entity;

import com.example.cheongchun28.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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

    @OneToOne
    @JoinColumn(name = "ROOM_SEQUENCE_ID", nullable = false)
    private Room room; // 강의실 ID
    //필드를 객체 타입으로 변경하는 것이 좋다..

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "USER_SEQUENCE_ID", nullable = false)
    //@Column(name = "USER_SEQUENCE_ID", nullable = false)
    private User user; // 예약한 사용자 ID

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt; // 예약 생성 일자

    @LastModifiedDate
    @Column(name = "MODIFIED_AT", nullable = false)
    private LocalDateTime modifiedAt; // 예약 수정 일자

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate; // 예약 시작 시간

    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate; // 예약 종료 시간

    @Column(name = "STATUS", nullable = false)
    private String status; // 예약 상태

    @Column(name = "TOPIC", nullable = false)
    private String topic; // 예약 목적

    @Column(name = "RESERVATION_CODE", nullable = false, unique = true)
    private String reservationCode;

    @Builder
    public void toEntity(String topic, LocalDateTime startDate, LocalDateTime endDate, Room room, User user, String status, String reservationCode){

        // 클라이언트에게 받을 값
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;

        // 부여해줘야하는 값
        this.room = room;
        this.user = user;
        this.status = status;
        this.reservationCode = reservationCode;

    }
}
