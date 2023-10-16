package com.geico.collections.quadtree;

import com.geico.collections.Geolocation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuadTreeTest {
    @Test
    public void testInsertAndQueryRange() {
        // Create a boundary covering an area from (0, 0) to (100, 100)
        Boundary boundary = new Boundary(0, 0, 100, 100);
        QuadTree<String> quadTree = new QuadTree<>(boundary);

        // Insert elements at specific geolocations
        quadTree.insert(new Geolocation(10, 10), "A");
        quadTree.insert(new Geolocation(20, 20), "B");
        quadTree.insert(new Geolocation(30, 30), "C");
        quadTree.insert(new Geolocation(40, 40), "D");

        // Define a query range covering an area from (5, 5) to (25, 25)
        Boundary queryRange = new Boundary(5, 5, 20, 20);

        // Query the quadtree for elements within the query range
        List<String> elementsInRange = quadTree.queryRange(queryRange);

        assertEquals(2, elementsInRange.size()); // Expecting "A" and "B" to be in the range
        assertTrue(elementsInRange.contains("A"));
        assertTrue(elementsInRange.contains("B"));
    }

    @Test
    public void testInsertAndSubdivide() {
        // Create a boundary covering an area from (0, 0) to (100, 100)
        Boundary boundary = new Boundary(0, 0, 100, 100);
        QuadTree<String> quadTree = new QuadTree<>(boundary);

        // Insert more than the capacity, which should trigger subdivision
        quadTree.insert(new Geolocation(10, 10), "A");
        quadTree.insert(new Geolocation(20, 20), "B");
        quadTree.insert(new Geolocation(30, 30), "C");
        quadTree.insert(new Geolocation(40, 40), "D");
        quadTree.insert(new Geolocation(50, 50), "E");
        quadTree.insert(new Geolocation(60, 60), "F");

        assertEquals(6, quadTree.queryRange(boundary).size());
    }
}
