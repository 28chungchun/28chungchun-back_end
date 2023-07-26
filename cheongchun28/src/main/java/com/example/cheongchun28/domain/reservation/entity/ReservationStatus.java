package com.example.cheongchun28.domain.reservation.entity;

public enum ReservationStatus {
    CONFIRMED(0, "예약 확정"),
    CANCELLED(1, "예약 취소"),
    COMPLETED(2, "예약 종료");

    private final int code;
    private final String description;

    ReservationStatus(int code, String description) {
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
