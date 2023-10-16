package com.geico.collections;

import lombok.Data;

/**
 * The `Distance` class represents a distance measurement that includes both a numeric value and a unit of measurement.
 */
@Data
public class Distance {
    private double value;     // The numeric value of the distance
    private DistanceUnit unit; // The unit of the distance (enum)

    /**
     * Constructs a `Distance` object with a specified numeric value and unit.
     *
     * @param value The numeric value of the distance.
     * @param unit  The unit of measurement (e.g., kilometers or miles).
     */
    public Distance(double value, DistanceUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    /**
     * Calculates the distance to a boundary limit based on the `Distance` object's value and unit.
     *
     * @param distance A `Distance` object representing the distance value and unit.
     * @return The calculated distance to the boundary limit.
     */
    public static double distanceToBoundaryLimit(Distance distance) {
        // If the unit is in miles, convert it to miles; otherwise, assume it's in kilometers.
        double miles = distance.unit == DistanceUnit.MILES ? distance.value : distance.value / 1.6;
        // Calculate the distance to the boundary limit using a constant value.
        return miles / 69.0;
    }
}