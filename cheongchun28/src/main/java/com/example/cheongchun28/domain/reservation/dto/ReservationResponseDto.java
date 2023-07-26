package com.example.cheongchun28.domain.reservation.dto;

import com.example.cheongchun28.domain.reservation.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReservationResponseDto { // 예약 응답 Dto

    @Getter
    @Setter
    public static class ReservationCreateResponseDto { // 예약 생성에 대한 응답값
        private int statusCode; // 예약 성공 실패 여부

        public ReservationCreateResponseDto(int statusCode){
            this.statusCode = statusCode;
        }
    }
    @Getter
    @Setter
    public static class ReservationGetResponseDto { // 예약 조회에 대한 응답값
        private int statusCode; // 예약 성공 실패 여부
        private String reservationCode; // 예약 번호

        public ReservationGetResponseDto(int statusCode, String reservationCode) {
            this.statusCode = statusCode;
            this.reservationCode = reservationCode;
        }
    }

    @Getter
    @Setter
    public static class ReservationGetOneResponseDto { // 예약 조회에 대한 응답값 (전체)
       // private int statusCode; // 예약 성공 실패 여부
        private String roomName;
        private String topic;
        private String username;
        private String status;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        public ReservationGetOneResponseDto(String roomName, String topic, String username, String status, LocalDateTime startDate, LocalDateTime endDate) {
           // this.statusCode = statusCode;
            this.roomName = roomName;
            this.topic = topic;
            this.username = username;
            this.status = status;
            this.startDate = startDate;
            this.endDate = endDate;
        }

    }

    @Getter
    @Setter
    public static class ReservationUpdateResponseDto { // 예약 수정에 대한 응답값
        private int statusCode; // 예약 성공 실패 여부

        public ReservationUpdateResponseDto(int statusCode) {

            this.statusCode = statusCode;
        }

        public ReservationUpdateResponseDto(boolean b, String s) {
            // 파라미터 값이 있는 생성자 생성
        }

    }

    @Getter
    @Setter
    public static class ReservationDeleteResponseDto { // 예약 삭제에 대한 응답값
        private String status; // 예약 현황
        private int statusCode; // 예약 성공 실패 여부

        public ReservationDeleteResponseDto(String status, int statusCode) {
            this.status = status;
            this.statusCode = statusCode;
        }
        public ReservationDeleteResponseDto(boolean b, String s) {
        }
    }
}
