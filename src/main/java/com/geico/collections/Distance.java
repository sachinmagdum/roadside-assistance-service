package com.geico.collections;

import lombok.Data;

@Data
public class Distance {
    private double value;     // The numeric value of the distance
    private DistanceUnit unit; // The unit of the distance (enum)

    public Distance(double value, DistanceUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public static double distanceToBoundaryLimit(Distance distance) {
        double miles = distance.unit == DistanceUnit.MILES ? distance.value : distance.value / 1.6;
        return miles / 69.0;
    }
}
