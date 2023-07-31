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

@Table(name = "t_reservation")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_sequence_id")
    private Long id;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_sequence_id", nullable = false)
    private Room room;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_sequence_id", nullable = false)
    private User user;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ReservationStatus status;


    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "reservation_code", unique = true, nullable = false)
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
