package com.geico.roadsideassistance.business.objects;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * The `Reservation` class represents a reservation made by a customer for assistance with a disabled vehicle.
 * It contains information about the reservation, including the reservation ID, customer, assistant, reservation
 * datetime, release datetime, and reservation status.
 */
@Data
public class Reservation {
    private String reservationId;      // A unique identifier for the reservation
    private Customer customer;         // The customer who made the reservation
    private Assistant assistant;       // The assistant assigned to the reservation
    private LocalDateTime reservationDatetime; // The date and time when the reservation was made
    private LocalDateTime releaseDatetime;     // The date and time when the reservation was released
    private ReservationStatus status;  // The status of the reservation (e.g., PENDING, CONFIRMED, CANCELLED, RELEASED)

    /**
     * Enumeration representing possible reservation statuses.
     */
    public enum ReservationStatus {
        PENDING,    // The reservation is pending confirmation
        CONFIRMED,  // The reservation is confirmed and assistance is on the way
        CANCELLED,  // The reservation has been cancelled by the customer
        RELEASED    // The reservation has been released, and the assistance is completed
    }

    /**
     * Constructor to create a new reservation.
     *
     * @param reservationId        A unique identifier for the reservation
     * @param customer             The customer who made the reservation
     * @param assistant            The assistant assigned to the reservation
     * @param reservationDatetime  The date and time when the reservation was made
     */
    public Reservation(String reservationId, Customer customer,
                       Assistant assistant, LocalDateTime reservationDatetime) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.assistant = assistant;
        this.reservationDatetime = reservationDatetime;
        this.status = ReservationStatus.PENDING; // By default, set the status to PENDING
    }
}