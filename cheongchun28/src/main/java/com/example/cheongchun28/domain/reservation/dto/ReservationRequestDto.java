package com.example.cheongchun28.domain.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
public class ReservationRequestDto { // 예약 요청 Dto
    //Dto란 클라이언트 측에서 요구한 사항들과, 클라이언트 측에 응답할 사항들을 캡슐화하여 모아놓은 상자

    @Getter
    @Setter
    public static class ReservationCreateRequestDto { // create. 우리가 받아야할 값

        private long reservationSequenceId;; // 예약 ID
        private int classSequenceId; // 강의실ID
        private long userSequenceId; // 회원 고유 ID
        private String topic; // 예약 목적
        private String userName; // 예약자 이름
        private LocalDateTime startDate; // 예약 시작 시간
        private LocalDateTime endDate; // 예약 종료 시간
        private String particName; // 참여자 이름
        private String particProfile; // 참여자 프로필 사진
        private int reservationState; // 예약 상태


        public ReservationCreateRequestDto(int classSequenceId, String topic, String userName, LocalDateTime startTime,  LocalDateTime endTime, String particName, String particProfile, int reservationState) {
            //강의실ID, topic, userName, start, end, particName, particProfile, state 초기화

            this.classSequenceId = classSequenceId; // 강의실 ID
            this.topic = topic;
            this.userName = userName;
            this.startDate = startDate;
            this.endDate = endDate;
            this.particName = particName;
            this.particProfile = particProfile;
            this.reservationState = reservationState;
        }



     /*   public Reservation toEntity() {
            return new Reservation(this.reservationSequenceId, this.classSequenceId, this.userSequenceId, this.topic, this.userName,
                    this.startTime, this.endTime, this.particName, this.particProfile, this. reservationState);
        }*/

        @Getter
        public static class ReservationGetRequestDto { // get. 우리가 받아야할 값

            //private int reservationId; // 예약 ID
            private int roomNum; // 강의실ID
            private String topic; // 예약 목적
            private LocalDateTime startTime; // 예약 시작 시간
            private LocalDateTime endTime; // 예약 종료 시간

        }
    }

}
