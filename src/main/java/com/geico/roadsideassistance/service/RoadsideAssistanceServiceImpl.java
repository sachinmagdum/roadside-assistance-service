package com.geico.roadsideassistance.service;

import com.geico.collections.Distance;
import com.geico.collections.DistanceUnit;
import com.geico.collections.Geolocation;
import com.geico.collections.quadtree.Boundary;
import com.geico.roadsideassistance.business.managers.AssistantManager;
import com.geico.roadsideassistance.business.managers.ReservationManager;
import com.geico.roadsideassistance.business.objects.Assistant;
import com.geico.roadsideassistance.business.objects.Customer;
import com.geico.roadsideassistance.business.objects.Reservation;
import lombok.Synchronized;

import java.util.*;

public class RoadsideAssistanceServiceImpl implements RoadsideAssistanceService {
    final private AssistantManager assistantManager;  // Instance of AssistantManager
    final private ReservationManager reservationManager;

    public RoadsideAssistanceServiceImpl() {
        Boundary serviceBoundary = new Boundary(-180, -90, 360, 180);
        Distance maxDistanceOfService = new Distance(100, DistanceUnit.MILES);
        assistantManager = new AssistantManager(serviceBoundary, maxDistanceOfService);
        reservationManager = new ReservationManager();
    }

    public RoadsideAssistanceServiceImpl(AssistantManager assistantManager,
                                         ReservationManager reservationManager) {
        this.assistantManager = assistantManager;
        this.reservationManager = reservationManager;
    }

        @Override
    public void updateAssistantLocation(Assistant assistant, Geolocation assistantLocation) {
        // Update the assistant's location using the AssistantManager
        assistantManager.updateAssistantLocation(assistant, assistantLocation);
    }

    @Override
    public List<Assistant> findNearestAssistants(Geolocation geolocation, int limit) {
        // Implement as before, potentially by delegating to the AssistantManager
        return assistantManager.findNearestAssistants(geolocation, limit);
    }

    @Override
    @Synchronized
    public Optional<Assistant> reserveAssistant(Customer customer, Geolocation customerLocation) {
        List<Assistant> nearestAssistants = findNearestAssistants(customerLocation, 1);

        if (!nearestAssistants.isEmpty()) {
            Assistant nearestAssistant = nearestAssistants.get(0);
            nearestAssistant.setStatus(Assistant.Status.RESERVED);

            // Create a reservation using the ReservationManager
            String reservationId = reservationManager.createReservation(customer, nearestAssistant);

            return Optional.of(nearestAssistant);
        }

        return Optional.empty();
    }

    @Override
    @Synchronized
    public void releaseAssistant(Customer customer, Assistant assistant) {
        // Check if the customer has a reservation with this assistant
        List<Reservation> reservations =
                reservationManager.getReservationsByCustomer(customer,
                        Reservation.ReservationStatus.CONFIRMED, Reservation.ReservationStatus.PENDING);

        for (Reservation reservation : reservations) {
            if (reservation.getAssistant().equals(assistant)) {
                // Release the reservation using the ReservationManager
                reservationManager.releaseReservation(reservation.getReservationId());
            }
        }
    }
}
