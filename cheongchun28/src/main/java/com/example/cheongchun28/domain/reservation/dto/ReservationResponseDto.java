package com.example.cheongchun28.domain.reservation.dto;

import com.example.cheongchun28.domain.reservation.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationResponseDto { // 예약 응답 Dto

    @Getter
    @Setter
    public static class ReservationCreateResponseDto { // 예약 생성에 대한 응답값
        private boolean success; // 예약 성공 실패 여부
    }

    @Getter
    @Setter
    public static class ReservationGetResponseDto { // 예약 조회에 대한 응답값
        private boolean success; // 예약 성공 실패 여부
        private long reservationId; // 예약 번호
    }

    @Getter
    @Setter
    public static class ReservationUpdateResponseDto { // 예약 수정에 대한 응답값
        private boolean success; // 예약 성공 실패 여부

        public ReservationUpdateResponseDto(boolean b, String s) {
        }

        public ReservationUpdateResponseDto(Reservation reservation) {
        }
    }

    @Getter
    @Setter
    public static class ReservationDeleteResponseDto { // 예약 삭제에 대한 응답값
        private boolean success; // 예약 성공 실패 여부

        public ReservationDeleteResponseDto(boolean b, String s) {
        }
    }
}
