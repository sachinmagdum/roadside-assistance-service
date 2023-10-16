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

public class ReservationManager {
    private Map<String, Reservation> reservations = new HashMap<>();
    private int nextReservationId = 1;

    public String createReservation(Customer customer, Assistant assistant) {
        if (assistant.getStatus() == Assistant.Status.AVAILABLE) {
            String reservationId = generateReservationId();
            LocalDateTime reservationDatetime = LocalDateTime.now();
            assistant.setStatus(Assistant.Status.RESERVED);
            Reservation reservation = new Reservation(reservationId, customer,
                    assistant, reservationDatetime);
            reservations.put(reservationId, reservation);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            return reservationId;
        }
        return null;
    }

    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }

    public List<Reservation> getReservationsByCustomer(Customer customer, ReservationStatus... statusFilter) {
        return reservations.values()
                .stream()
                .filter(reservation -> reservation.getCustomer().equals(customer)
                        && (statusFilter.length == 0 || containsStatus(statusFilter, reservation.getStatus())))
                .collect(Collectors.toList());
    }

    public List<Reservation> getReservationsByAssistant(Assistant assistant, ReservationStatus... statusFilter) {
        return reservations.values()
                .stream()
                .filter(reservation -> reservation.getAssistant().equals(assistant)
                        && (statusFilter.length == 0 || containsStatus(statusFilter, reservation.getStatus())))
                .collect(Collectors.toList());
    }

    public void releaseReservation(String reservationId) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation != null) {
            reservation.setStatus(ReservationStatus.RELEASED);
            reservation.getAssistant().setStatus(Assistant.Status.AVAILABLE);
        }
    }

    private String generateReservationId() {
        return "R" + nextReservationId++;
    }

    private boolean containsStatus(ReservationStatus[] statusFilter, ReservationStatus status) {
        for (ReservationStatus filter : statusFilter) {
            if (filter == status) {
                return true;
            }
        }
        return false;
    }
}
