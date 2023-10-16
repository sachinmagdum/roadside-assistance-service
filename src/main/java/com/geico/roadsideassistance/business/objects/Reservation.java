package com.geico.roadsideassistance.business.objects;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reservation {
    private String reservationId;
    private Customer customer;
    private Assistant assistant;
    private LocalDateTime reservationDatetime;
    private LocalDateTime releaseDatetime;
    private ReservationStatus status;

    public enum ReservationStatus {
        PENDING,
        CONFIRMED,
        CANCELLED,
        RELEASED
    }

    public Reservation(String reservationId, Customer customer,
                       Assistant assistant, LocalDateTime reservationDatetime) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.assistant = assistant;
        this.reservationDatetime = reservationDatetime;
        this.status = ReservationStatus.PENDING;
    }
}