package com.geico.collections.quadtree;

import com.geico.collections.Geolocation;
import lombok.Data;

@Data
public class Boundary {
    private double x;      // The x-coordinate of the top-left corner
    private double y;      // The y-coordinate of the top-left corner
    private double width;  // The width of the boundary
    private double height; // The height of the boundary

    public Boundary(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public static Boundary getBoundaryFromCenter(Geolocation center, double distance) {
        // Calculate the boundaries based on the center point and distance limit
        double x = center.getLatitude() - distance;
        double y = center.getLongitude() - distance;

        return new Boundary(x, y, 2*distance, 2*distance);
    }

    public boolean contains(Geolocation point) {
        // Check if the boundary contains the given point
        double px = point.getLatitude();
        double py = point.getLongitude();
        return px >= x && px < (x + width) && py >= y && py < (y + height);
    }

    public boolean intersects(Boundary other) {
        // Check if this boundary intersects with another boundary
        return x < other.x + other.width && x + width > other.x && y < other.y + other.height && y + height > other.y;
    }
}


