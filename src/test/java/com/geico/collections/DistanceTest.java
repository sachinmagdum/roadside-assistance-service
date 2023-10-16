package com.geico.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistanceTest {

    @Test
    public void testDistanceToBoundaryLimitMiles() {
        // Given a distance of 10 miles
        Distance distance = new Distance(10, DistanceUnit.MILES);

        // Calculate the distance to boundary limit
        double result = Distance.distanceToBoundaryLimit(distance);

        // Expected result is 10 miles / 69.0
        assertEquals(10.0 / 69.0, result, 0.001); // Allowing a small delta for floating-point comparison
    }

    @Test
    public void testDistanceToBoundaryLimitKilometers() {
        // Given a distance of 16 kilometers (equivalent to 10 miles)
        Distance distance = new Distance(16, DistanceUnit.KILOMETERS);

        // Calculate the distance to boundary limit
        double result = Distance.distanceToBoundaryLimit(distance);

        // Expected result is 10 miles / 69.0
        assertEquals(10.0 / 69.0, result, 0.001); // Allowing a small delta for floating-point comparison
    }

    @Test
    public void testDistanceToBoundaryLimitZero() {
        // Given a distance of 0 miles
        Distance distance = new Distance(0, DistanceUnit.MILES);

        // Calculate the distance to boundary limit
        double result = Distance.distanceToBoundaryLimit(distance);

        // Expected result is 0
        assertEquals(0.0, result, 0.001); // Allowing a small delta for floating-point comparison
    }
}
