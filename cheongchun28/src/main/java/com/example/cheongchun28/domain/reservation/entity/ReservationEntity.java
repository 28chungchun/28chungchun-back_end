package com.example.cheongchun28.domain.reservation.entity;

import com.example.cheongchun28.domain.user.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Data
@Entity
@Table(name = "T_RESERVATION")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RESERVATION_ID", nullable = false)
    private Long reservationId;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT") // nullable = true
    private LocalDateTime modifiedAt;

    @Column(name = "START_TIME", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "END_TIME", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "PURPOSE", nullable = false) //ERD상으론 REASON인데 purpose는 목적, reason는 약간 변명 이런 성향의 단어여서...
    private String purpose;

    //인원은 빼고 인원들이 예약 입장 토큰을 가져간다고 해서 뻈습니다.

    @JoinColumn(name = "CLASS_SEQUENCE_ID", nullable = false) //패치타입을 eager로 하면 유저 엔티티가 필요 없을때도 항시 로딩됨
    private Long classSequenceID;

    @ManyToOne(fetch = FetchType.LAZY) // ERD가 다대일인지 일대다인지 공부가 더 필요하지만...
    @JoinColumn(name = "USER_SEQUENCE_ID", nullable = false)
    private UserEntity userSequenceId;

    public ReservationEntity(LocalDateTime startTime, LocalDateTime endTime, String purpose, Long classSequenceID, UserEntity userSequenceId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.purpose = purpose;
        this.classSequenceID = classSequenceID;
        this.userSequenceId = userSequenceId;
    }
}