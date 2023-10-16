package com.geico.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeolocationTest {

    @Test
    public void testDistanceToSameLocation() {
        Geolocation location = new Geolocation(40.0, -73.0);

        // Distance to itself should be 0
        double distance = location.milesTo(location);
        assertEquals(0.0, distance, 0.001); // Allow a small delta for floating-point comparison
    }

    @Test
    public void testDistanceToDifferentLocation() {
        Geolocation location1 = new Geolocation(40.0, -73.0);
        Geolocation location2 = new Geolocation(45.0, -75.0);

        // Expected distance between the two locations
        double expectedDistance = 360.16; // Approximate value in miles

        double distance = location1.milesTo(location2);
        assertEquals(expectedDistance, distance, 1.0); // Allow a larger delta due to approximation
    }

    @Test
    public void testDistanceToDifferentLocationInMiles() {
        Geolocation location1 = new Geolocation(31.0, -70.0);
        Geolocation location2 = new Geolocation(49.0, -82.0);

        // Expected distance between the two locations in miles
        double expectedDistanceInMiles = 1392.61; // Approximate value in miles

        double distance = location1.milesTo(location2);
        assertEquals(expectedDistanceInMiles,
                distance, 1.0); // Allow a larger delta due to approximation
    }
}
