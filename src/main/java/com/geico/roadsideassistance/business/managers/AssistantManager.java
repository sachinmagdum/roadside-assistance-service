package com.geico.roadsideassistance.business.managers;

import com.geico.collections.Distance;
import com.geico.collections.Geolocation;
import com.geico.collections.quadtree.Boundary;
import com.geico.collections.quadtree.QuadTree;
import com.geico.roadsideassistance.business.objects.Assistant;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The `AssistantManager` class is responsible for managing assistants and their geolocations in the Geico roadside assistance service.
 * It allows updating assistant locations, finding nearest available assistants, and specifying a service boundary.
 */
public class AssistantManager {
    final private QuadTree<Assistant> geolocationQuadTree;
    final private double maxLimitOfServiceBoundary;

    /**
     * Initializes an `AssistantManager` with a specified service boundary and maximum distance of service.
     *
     * @param serviceBoundary      The geographical boundary for the roadside assistance service.
     * @param maxDistanceOfService The maximum distance within which assistants are available.
     */
    public AssistantManager(Boundary serviceBoundary, Distance maxDistanceOfService) {
        geolocationQuadTree = new QuadTree<Assistant>(serviceBoundary);
        maxLimitOfServiceBoundary = Distance.distanceToBoundaryLimit(maxDistanceOfService);
    }

    /**
     * Initializes an `AssistantManager` with a predefined `QuadTree` and a maximum distance of service.
     *
     * @param geolocationQuadTree  The `QuadTree` structure containing assistant geolocations.
     * @param maxDistanceOfService The maximum distance within which assistants are available.
     */
    public AssistantManager(QuadTree<Assistant> geolocationQuadTree, Distance maxDistanceOfService) {
        this.geolocationQuadTree = geolocationQuadTree;
        maxLimitOfServiceBoundary = Distance.distanceToBoundaryLimit(maxDistanceOfService);
    }

    /**
     * Updates the geolocation of an assistant and stores the change in the `QuadTree`.
     *
     * @param assistant          The assistant whose location is being updated.
     * @param assistantLocation  The new geolocation of the assistant.
     */
    public void updateAssistantLocation(Assistant assistant, Geolocation assistantLocation) {
        // Update the assistant's location in the QuadTree
        geolocationQuadTree.insert(assistantLocation, assistant);
        // Update the assistant's location
        assistant.setGeolocation(assistantLocation);
    }

    /**
     * Finds the nearest available assistants to a given geolocation within a specified limit.
     *
     * @param geolocation The geolocation for which to find the nearest assistants.
     * @param limit       The maximum number of assistants to return.
     * @return A list of the nearest available assistants, limited by the specified count.
     */
    public List<Assistant> findNearestAssistants(Geolocation geolocation, int limit) {
        Boundary boundary = Boundary.getBoundaryFromCenter(geolocation, maxLimitOfServiceBoundary);
        List<Assistant> nearbyAssistants = geolocationQuadTree.queryRange(boundary);

        SortedSet<Assistant> nearestAssistants = new TreeSet<Assistant>(Comparator.comparingDouble(
                a -> a.getGeolocation().milesTo(geolocation)));

        for (Assistant assistant : nearbyAssistants) {
            if (assistant.getStatus() == Assistant.Status.AVAILABLE) {
                nearestAssistants.add(assistant);
            }
        }

        // Limit the result to the requested number
        return nearestAssistants.stream()
                .limit(limit)
                .collect(Collectors.toCollection(ArrayList<Assistant>::new));
    }
}