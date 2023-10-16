package com.geico.collections;

import lombok.Getter;

/**
 * The `Geolocation` class represents a geographic location specified by latitude and longitude coordinates.
 */
@Getter
public class Geolocation {
    private double latitude;    // The latitude of the geographic location.
    private double longitude;   // The longitude of the geographic location.

    /**
     * Initializes a `Geolocation` object with the given latitude and longitude coordinates.
     *
     * @param latitude  The latitude of the geographic location.
     * @param longitude The longitude of the geographic location.
     */
    public Geolocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Calculate the Haversine distance in miles to another geolocation.
     *
     * @param other The target `Geolocation` to calculate the distance to.
     * @return The distance in miles between this `Geolocation` and the target `Geolocation`.
     */
    public double milesTo(Geolocation other) {
        double earthRadius = 3959; // Radius of the Earth in miles

        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(other.latitude);
        double lon2 = Math.toRadians(other.longitude);

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }
}