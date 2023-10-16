package com.geico.roadsideassistance.service;

import com.geico.collections.Geolocation;
import com.geico.roadsideassistance.business.managers.AssistantManager;
import com.geico.roadsideassistance.business.managers.ReservationManager;
import com.geico.roadsideassistance.business.objects.Assistant;
import com.geico.roadsideassistance.business.objects.Customer;
import com.geico.roadsideassistance.business.objects.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class RoadsideAssistanceServiceImplTest {

    private RoadsideAssistanceServiceImpl roadsideAssistanceService;
    private AssistantManager assistantManager;
    private ReservationManager reservationManager;

    @BeforeEach
    public void setUp() {
        assistantManager = mock(AssistantManager.class);
        reservationManager = mock(ReservationManager.class);
        roadsideAssistanceService =
                new RoadsideAssistanceServiceImpl(assistantManager, reservationManager);
    }

    @Test
    public void testUpdateAssistantLocation() {
        Assistant assistant = mock(Assistant.class);
        Geolocation location = new Geolocation(40.0, -73.0);

        roadsideAssistanceService.updateAssistantLocation(assistant, location);

        verify(assistantManager).updateAssistantLocation(assistant, location);
    }

    @Test
    public void testFindNearestAssistants() {
        Geolocation customerLocation = new Geolocation(40.0, -73.0);
        int limit = 5;
        List<Assistant> expectedAssistants = new ArrayList<>();
        when(assistantManager.findNearestAssistants(customerLocation, limit)).thenReturn(expectedAssistants);

        List<Assistant> result = roadsideAssistanceService.findNearestAssistants(customerLocation, limit);

        assertEquals(expectedAssistants, result);
    }

    @Test
    public void testReserveAssistant() {
        Customer customer = mock(Customer.class);
        Geolocation customerLocation = new Geolocation(40.0, -73.0);
        Assistant assistant = mock(Assistant.class);
        List<Assistant> nearestAssistants = new ArrayList<>();
        nearestAssistants.add(assistant);

        when(assistantManager.findNearestAssistants(customerLocation, 1)).thenReturn(nearestAssistants);
        when(reservationManager.createReservation(customer, assistant)).thenReturn("reservation123");

        Optional<Assistant> result = roadsideAssistanceService.reserveAssistant(customer, customerLocation);

        assertTrue(result.isPresent());
        assertEquals(assistant, result.get());
    }

    @Test
    public void testReleaseAssistant() {
        Customer customer = mock(Customer.class);
        Assistant assistant = mock(Assistant.class);
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation = mock(Reservation.class);
        reservations.add(reservation);

        when(reservationManager.getReservationsByCustomer(customer,
                Reservation.ReservationStatus.CONFIRMED, Reservation.ReservationStatus.PENDING))
                .thenReturn(reservations);
        when(reservation.getAssistant()).thenReturn(assistant);
        String reservationId = "12432343";
        when(reservation.getReservationId()).thenReturn(reservationId);

        roadsideAssistanceService.releaseAssistant(customer, assistant);

        verify(reservationManager).releaseReservation(reservationId);
    }
}
