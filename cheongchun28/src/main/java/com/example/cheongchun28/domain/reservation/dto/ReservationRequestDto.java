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
    @NoArgsConstructor //-> 얘 있으면 기본 생성자 만들어짐
    public static class ReservationCreateRequestDto { // create. 우리가 받아야할 값

        private String roomName; // 강의실명
        private String topic; // 예약 목적
        private LocalDateTime startDate; // 예약 시작 시간
        private LocalDateTime endDate; // 예약 종료 시간

        //인자를 받아 해당 값들로 객체를 초기화하기 위한 생성자
        public ReservationCreateRequestDto(String roomName, String topic, LocalDateTime startDate, LocalDateTime endDate) {
            this.roomName = roomName;
            this.topic = topic;
            this.startDate = startDate;
            this.endDate = endDate;
        }

    }

    @Getter
    @Setter
    public static class ReservationGetRequestDto{



    }


    @Setter
    @Getter
    public static class ReservationUpdateRequestDto { // update. 우리가 받아야할 값

        private String topic; // 예약 목적
        private LocalDateTime startDate; // 예약 시작 시간
        private LocalDateTime endDate; // 예약 종료 시간

        public ReservationUpdateRequestDto updateUserSequenceId() { // 업데이트하는 용도!!

        // 사용자 시퀀스 ID를 어떻게 업데이트할지를 구현해야함
            return updateUserSequenceId();
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor //-> 얘 있으면 기본 생성자 만들어짐
    public static class ReservationDeleteRequestDto { // delete. 우리가 받아야할 값

        private long userSequenceId; // 회원 고유 ID
        private long reservationSequenceId; // 예약 고유 ID
        private String status; // 예약 상태

        public ReservationDeleteRequestDto(long userSequenceId, long reservationSequenceId, String status) {
            this.userSequenceId = userSequenceId;
            this.reservationSequenceId = reservationSequenceId;
            this.status = status;
        }
    }

    /*   public Reservation toEntity() {
            return new Reservation(this.reservationSequenceId, this.classSequenceId, this.userSequenceId, this.topic, this.userName,
                    this.startTime, this.endTime, this.particName, this.particProfile, this. reservationState);
        }*/

}
