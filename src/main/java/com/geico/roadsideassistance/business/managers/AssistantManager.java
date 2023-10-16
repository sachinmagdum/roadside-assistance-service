package com.geico.roadsideassistance.business.managers;

import com.geico.collections.Distance;
import com.geico.collections.Geolocation;
import com.geico.collections.quadtree.Boundary;
import com.geico.collections.quadtree.QuadTree;
import com.geico.roadsideassistance.business.objects.Assistant;

import java.util.*;
import java.util.stream.Collectors;

public class AssistantManager {
    final private QuadTree<Assistant> geolocationQuadTree;
    final private double maxLimitOfServiceBoundary;

    public AssistantManager(Boundary serviceBoundary, Distance maxDistanceOfService) {
        geolocationQuadTree = new QuadTree<Assistant>(serviceBoundary);
        maxLimitOfServiceBoundary = Distance.distanceToBoundaryLimit(maxDistanceOfService);
    }

    public AssistantManager(QuadTree<Assistant> geolocationQuadTree, Distance maxDistanceOfService) {
        this.geolocationQuadTree = geolocationQuadTree;
        maxLimitOfServiceBoundary = Distance.distanceToBoundaryLimit(maxDistanceOfService);
    }

    public void updateAssistantLocation(Assistant assistant, Geolocation assistantLocation) {
        // Update the assistant's location in the QuadTree
        geolocationQuadTree.insert(assistantLocation, assistant);
        // Update the assistant's location
        assistant.setGeolocation(assistantLocation);
    }

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
