package com.example.cheongchun28.domain.reservation.entity;


import com.example.cheongchun28.domain.reservation.dto.ReservationRequestDto;
import com.example.cheongchun28.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "T_RESERVATION")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_SEQUENCE_ID")
    private Long id;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLASS_SEQUENCE_ID", nullable = false)
    private Room room;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQUENCE_ID", nullable = false)
    private User user;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "START_TIME", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "END_TIME", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ReservationStatus status;


    @Column(name = "TOPIC", nullable = false)
    private String topic;

    @Column(name = "RESERVATION_CODE", unique = true, nullable = false)
    private String code;

//    public String generateCode() {
//        return UUID.randomUUID().toString().replace("-", "");
//    }

    private String createReservationCoder() {
        return RandomString.make(8);
    }

    @Builder
    public Reservation(Room room, User user, LocalDateTime startDate, LocalDateTime endDate, ReservationStatus status, String topic) {
        this.room = room;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.topic = topic;
        this.code = createReservationCoder();
    }

    public void updateReservation(ReservationRequestDto.UpdateReservationDto updateReservationReqDto) {

        this.startDate = updateReservationReqDto.getStartDate();
        this.endDate = updateReservationReqDto.getEndDate();
        this.topic = updateReservationReqDto.getTopic();
    }


    public void deleteReservation(){
        this.status = ReservationStatus.CANCELLED;
    }
}
