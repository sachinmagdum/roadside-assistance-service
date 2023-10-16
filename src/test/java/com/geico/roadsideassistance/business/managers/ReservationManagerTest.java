package com.geico.roadsideassistance.business.managers;

import com.geico.collections.*;
import com.geico.roadsideassistance.business.objects.Assistant;
import com.geico.roadsideassistance.business.objects.Customer;
import com.geico.roadsideassistance.business.objects.GeicoIdentifier;
import com.geico.roadsideassistance.business.objects.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationManagerTest {

    private ReservationManager reservationManager;
    private Assistant assistant;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        reservationManager = new ReservationManager();
        assistant = new Assistant(
                new Geolocation(40.1, -73.1),
                new Name("Assistant", "One"),
                new Contact("assistant1@example.com", "111-111-1111"),
                new GeicoIdentifier("111111")
        );

        customer = new Customer(
                new Name("Sree", "Vaju"),
                new Contact("sree.vaju@example.com", "123-456-1234"),
                new Geolocation(41.0, -75.0),
                new GeicoIdentifier("123456")
        );
    }

    @Test
    public void testCreateReservation() {
        // Create a reservation
        String reservationId = reservationManager.createReservation(customer, assistant);

        assertNotNull(reservationId);
        Reservation reservation = reservationManager.getReservation(reservationId);

        assertNotNull(reservation);
        assertEquals(Reservation.ReservationStatus.CONFIRMED, reservation.getStatus());
        assertEquals(customer, reservation.getCustomer());
        assertEquals(assistant, reservation.getAssistant());
    }

    @Test
    public void testCreateReservationWithUnavailableAssistant() {
        // Set the assistant's status to UNAVAILABLE
        assistant.setStatus(Assistant.Status.OFF_DUTY);

        // Try to create a reservation with an unavailable assistant
        String reservationId = reservationManager.createReservation(customer, assistant);

        assertNull(reservationId);
        assertNull(reservationManager.getReservation(reservationId));
    }

    @Test
    public void testReleaseReservation() {
        // Create a reservation
        String reservationId = reservationManager.createReservation(customer, assistant);

        // Release the reservation
        reservationManager.releaseReservation(reservationId);

        Reservation reservation = reservationManager.getReservation(reservationId);

        assertNotNull(reservation);
        assertEquals(Reservation.ReservationStatus.RELEASED, reservation.getStatus());
        assertEquals(Assistant.Status.AVAILABLE, assistant.getStatus());
    }

    @Test
    public void testGetReservationsByCustomer() {
        // Create multiple reservations for the same customer
        Customer customer2 = new Customer(
                new Name("John", "Doe"),
                new Contact("john.doe@example.com", "123-456-7890"),
                new Geolocation(40.0, -73.0),
                new GeicoIdentifier("123456")
        );

        reservationManager.createReservation(customer, assistant);
        reservationManager.createReservation(customer, assistant);
        reservationManager.createReservation(customer2, assistant);

        // Get reservations for the customer
        var reservations = reservationManager.getReservationsByCustomer(customer);

        assertNotNull(reservations);
        assertEquals(1, reservations.size());

        // Get reservations for the customer
        var reservations2 = reservationManager.getReservationsByCustomer(customer2);
        assertNotNull(reservations2);
        assertEquals(0, reservations2.size());
    }
}
