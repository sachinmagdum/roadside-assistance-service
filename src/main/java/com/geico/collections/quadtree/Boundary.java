package com.geico.collections.quadtree;

import com.geico.collections.Geolocation;
import lombok.Data;

/**
 * The `Boundary` class represents a rectangular boundary with specified dimensions.
 * It is used in the `QuadTree` class to define the boundaries of partitions within the quadtree.
 */
@Data
public class Boundary {
    private double x;      // The x-coordinate of the top-left corner
    private double y;      // The y-coordinate of the top-left corner
    private double width;  // The width of the boundary
    private double height; // The height of the boundary

    /**
     * Constructs a `Boundary` with the given coordinates and dimensions.
     *
     * @param x      The x-coordinate of the top-left corner.
     * @param y      The y-coordinate of the top-left corner.
     * @param width  The width of the boundary.
     * @param height The height of the boundary.
     */
    public Boundary(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Creates a boundary around a central geolocation point with a specified distance.
     *
     * @param center   The central geolocation point.
     * @param distance The radius distance from the center to create the boundary.
     * @return A boundary defined by the center and the specified distance.
     */
    public static Boundary getBoundaryFromCenter(Geolocation center, double distance) {
        // Calculate the boundaries based on the center point and distance limit
        double x = center.getLatitude() - distance;
        double y = center.getLongitude() - distance;

        return new Boundary(x, y, 2 * distance, 2 * distance);
    }

    /**
     * Checks if the boundary contains a given geolocation point.
     *
     * @param point The geolocation point to check for containment.
     * @return `true` if the boundary contains the point, `false` otherwise.
     */
    public boolean contains(Geolocation point) {
        double px = point.getLatitude();
        double py = point.getLongitude();
        return px >= x && px < (x + width) && py >= y && py < (y + height);
    }

    /**
     * Checks if this boundary intersects with another boundary.
     *
     * @param other The other boundary to check for intersection.
     * @return `true` if this boundary intersects with the other boundary, `false` otherwise.
     */
    public boolean intersects(Boundary other) {
        return x < other.x + other.width && x + width > other.x && y < other.y + other.height && y + height > other.y;
    }
}