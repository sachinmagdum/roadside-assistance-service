package com.geico.roadsideassistance.business.objects;

import lombok.Data;

/**
 * The `GeicoIdentifier` class represents a unique identifier associated with a Geico customer or assistant.
 * It holds a string-based `geicoId` that uniquely identifies a customer or assistant within the Geico system.
 */
@Data
public class GeicoIdentifier {

    private String geicoId; // The unique identifier associated with a Geico customer or assistant

    /**
     * Constructor to create a new `GeicoIdentifier` with a specified identifier.
     *
     * @param geicoId The unique identifier (e.g., customer ID or assistant ID) associated with Geico.
     */
    public GeicoIdentifier(String geicoId) {
        this.geicoId = geicoId;
    }
}
