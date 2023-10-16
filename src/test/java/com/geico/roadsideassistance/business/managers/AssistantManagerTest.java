package com.geico.roadsideassistance.business.managers;

import com.geico.collections.*;
import com.geico.collections.quadtree.Boundary;
import com.geico.collections.quadtree.QuadTree;
import com.geico.roadsideassistance.business.objects.Assistant;

import com.geico.roadsideassistance.business.objects.GeicoIdentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class AssistantManagerTest {

    private AssistantManager assistantManager;
    private QuadTree<Assistant> geolocationQuadTree;
    private double maxLimitOfServiceBoundary;

    @BeforeEach
    public void setUp() {
        // Mock the QuadTree and set a known maxLimitOfServiceBoundary
        geolocationQuadTree = mock(QuadTree.class);
        maxLimitOfServiceBoundary = 10.0; // Example value

        assistantManager = new AssistantManager(geolocationQuadTree,
                new Distance(maxLimitOfServiceBoundary, DistanceUnit.MILES));
    }

    @Test
    public void testUpdateAssistantLocation() {
        Assistant assistant = new Assistant(
                new Geolocation(40.0, -73.0),
                new Name("John", "Doe"),
                new Contact("john.doe@example.com", "123-456-7890"),
                new GeicoIdentifier("123456")
        );

        Geolocation location = new Geolocation(40.5, -73.5);

        // Mock the QuadTree's insert method
        when(geolocationQuadTree.insert(location, assistant)).thenReturn(true);

        assistantManager.updateAssistantLocation(assistant, location);

        // Verify that the assistant's location was updated and QuadTree's insert method was called
        assertEquals(location, assistant.getGeolocation());
        verify(geolocationQuadTree, times(1)).insert(location, assistant);
    }

    @Test
    public void testFindNearestAssistants() {
        Geolocation location = new Geolocation(40.0, -73.0);
        int limit = 5;

        // Create a mock list of nearby assistants
        Assistant assistant1 = new Assistant(
                new Geolocation(40.1, -73.1),
                new Name("Assistant", "One"),
                new Contact("assistant1@example.com", "111-111-1111"),
                new GeicoIdentifier("111111")
        );
        assistant1.setStatus(Assistant.Status.AVAILABLE);

        Assistant assistant2 = new Assistant(
                new Geolocation(40.2, -73.2),
                new Name("Assistant", "Two"),
                new Contact("assistant2@example.com", "222-222-2222"),
                new GeicoIdentifier("222222")
        );
        assistant2.setStatus(Assistant.Status.OFF_DUTY);

        Assistant assistant3 = new Assistant(
                new Geolocation(40.3, -73.3),
                new Name("Assistant", "Three"),
                new Contact("assistant3@example.com", "333-333-3333"),
                new GeicoIdentifier("333333")
        );
        assistant3.setStatus(Assistant.Status.AVAILABLE);

        // Mock the QuadTree's queryRange method
        when(geolocationQuadTree.queryRange(any())).thenReturn(List.of(assistant1, assistant2, assistant3));

        List<Assistant> result = assistantManager.findNearestAssistants(location, limit);

        // Verify that only available assistants are in the result and limited by the specified limit
        assertEquals(2, result.size());
    }
}
