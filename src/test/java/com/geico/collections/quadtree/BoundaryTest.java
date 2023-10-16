package com.geico.collections.quadtree;

import com.geico.collections.Geolocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoundaryTest {

    @Test
    public void testContainsPointInsideBoundary() {
        Boundary boundary = new Boundary(0, 0, 10, 10);
        Geolocation pointInside = new Geolocation(5, 5);

        assertTrue(boundary.contains(pointInside));
    }

    @Test
    public void testContainsPointOutsideBoundary() {
        Boundary boundary = new Boundary(0, 0, 10, 10);
        Geolocation pointOutside = new Geolocation(15, 15);

        assertFalse(boundary.contains(pointOutside));
    }

    @Test
    public void testIntersectsBoundary() {
        Boundary boundary1 = new Boundary(0, 0, 10, 10);
        Boundary boundary2 = new Boundary(5, 5, 10, 10);

        assertTrue(boundary1.intersects(boundary2));
    }

    @Test
    public void testDoesNotIntersectBoundary() {
        Boundary boundary1 = new Boundary(0, 0, 5, 5);
        Boundary boundary2 = new Boundary(10, 10, 5, 5);

        assertFalse(boundary1.intersects(boundary2));
    }

    @Test
    public void testGetBoundaryFromCenter() {
        Geolocation center = new Geolocation(0, 0);
        double distance = 10;

        Boundary resultBoundary = Boundary.getBoundaryFromCenter(center, distance);

        assertEquals(-10, resultBoundary.getX());
        assertEquals(-10, resultBoundary.getY());
        assertEquals(20, resultBoundary.getWidth());
        assertEquals(20, resultBoundary.getHeight());
    }
}
