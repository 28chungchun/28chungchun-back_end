package com.example.cheongchun28.domain.reservation.entity;

public enum ReservationMemberStatus {

    CONFIRMED(0, "예약 참가"),
    CANCELLED(1, "참가 취소");

    private final int code;
    private final String description;

    ReservationMemberStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
