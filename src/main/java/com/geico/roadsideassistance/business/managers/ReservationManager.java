package com.geico.roadsideassistance.business.managers;

import com.geico.roadsideassistance.business.objects.Assistant;
import com.geico.roadsideassistance.business.objects.Customer;
import com.geico.roadsideassistance.business.objects.Reservation;
import com.geico.roadsideassistance.business.objects.Reservation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The `ReservationManager` class is responsible for managing reservations in Geico's roadside assistance service.
 * It allows creating, retrieving, and releasing reservations, as well as filtering reservations by customer or assistant.
 */
public class ReservationManager {
    private Map<String, Reservation> reservations = new HashMap<>();
    private int nextReservationId = 1;

    /**
     * Creates a new reservation for a customer with an available assistant.
     *
     * @param customer   The customer for whom the reservation is created.
     * @param assistant  The assistant assigned to the reservation.
     * @return The reservation ID if the assistant is available, or `null` if the assistant is not available.
     */
    public String createReservation(Customer customer, Assistant assistant) {
        if (assistant.getStatus() == Assistant.Status.AVAILABLE) {
            String reservationId = generateReservationId();
            LocalDateTime reservationDatetime = LocalDateTime.now();
            assistant.setStatus(Assistant.Status.RESERVED);
            Reservation reservation = new Reservation(reservationId, customer, assistant, reservationDatetime);
            reservations.put(reservationId, reservation);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            return reservationId;
        }
        return null;
    }

    /**
     * Retrieves a reservation by its ID.
     *
     * @param reservationId The unique identifier of the reservation.
     * @return The reservation associated with the given ID, or `null` if not found.
     */
    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }

    /**
     * Retrieves a list of reservations for a specific customer, optionally filtered by status.
     *
     * @param customer     The customer for whom reservations are retrieved.
     * @param statusFilter Optional filters for reservation status.
     * @return A list of reservations matching the customer and status filters.
     */
    public List<Reservation> getReservationsByCustomer(Customer customer, ReservationStatus... statusFilter) {
        return reservations.values()
                .stream()
                .filter(reservation -> reservation.getCustomer().equals(customer)
                        && (statusFilter.length == 0 || containsStatus(statusFilter, reservation.getStatus())))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of reservations for a specific assistant, optionally filtered by status.
     *
     * @param assistant    The assistant for whom reservations are retrieved.
     * @param statusFilter Optional filters for reservation status.
     * @return A list of reservations matching the assistant and status filters.
     */
    public List<Reservation> getReservationsByAssistant(Assistant assistant, ReservationStatus... statusFilter) {
        return reservations.values()
                .stream()
                .filter(reservation -> reservation.getAssistant().equals(assistant)
                        && (statusFilter.length == 0 || containsStatus(statusFilter, reservation.getStatus())))
                .collect(Collectors.toList());
    }

    /**
     * Releases a reservation, setting its status to RELEASED and marking the assistant as AVAILABLE.
     *
     * @param reservationId The unique identifier of the reservation to be released.
     */
    public void releaseReservation(String reservationId) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation != null) {
            reservation.setStatus(ReservationStatus.RELEASED);
            reservation.getAssistant().setStatus(Assistant.Status.AVAILABLE);
        }
    }

    /**
     * Generates a unique reservation ID.
     *
     * @return A unique reservation ID.
     */
    private String generateReservationId() {
        return "R" + nextReservationId++;
    }

    /**
     * Checks if a given status is contained within a list of status filters.
     *
     * @param statusFilter The array of status filters to check against.
     * @param status       The status to be checked for containment.
     * @return `true` if the status is contained in the filters, or `false` otherwise.
     */
    private boolean containsStatus(ReservationStatus[] statusFilter, ReservationStatus status) {
        for (ReservationStatus filter : statusFilter) {
            if (filter == status) {
                return true;
            }
        }
        return false;
    }
}