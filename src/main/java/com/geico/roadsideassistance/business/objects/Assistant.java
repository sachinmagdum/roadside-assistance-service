package com.geico.roadsideassistance.business.objects;

import com.geico.collections.Contact;
import com.geico.collections.Geolocation;
import com.geico.collections.Name;
import lombok.Data;

/**
 * The `Assistant` class represents a roadside assistance service provider, often referred to as an assistant.
 * It contains information about the assistant's geolocation, status, name, contact details, and a unique Geico identifier.
 */
@Data
public class Assistant {
    private Geolocation geolocation;  // The current geolocation of the assistant
    private Status status;            // The status of the assistant (e.g., RESERVED, AVAILABLE, OFF_DUTY)
    private Name name;                // The name of the assistant
    private Contact contact;          // Contact details of the assistant
    private GeicoIdentifier geicoId;  // A unique identifier associated with the assistant

    /**
     * Enumeration representing possible assistant statuses.
     */
    public enum Status {
        RESERVED,   // The assistant is reserved for a customer
        AVAILABLE,  // The assistant is available for new requests
        OFF_DUTY    // The assistant is currently off-duty
    }

    /**
     * Constructor to create a new `Assistant` instance with the provided details.
     *
     * @param geolocation The current geolocation of the assistant
     * @param name        The name of the assistant
     * @param contact     Contact details of the assistant
     * @param geicoId     A unique identifier associated with the assistant
     */
    public Assistant(Geolocation geolocation, Name name, Contact contact, GeicoIdentifier geicoId) {
        this.geolocation = geolocation;
        this.status = Status.AVAILABLE; // By default, assistants are available
        this.name = name;
        this.contact = contact;
        this.geicoId = geicoId;
    }

    /**
     * Custom implementation of the `equals` method to compare assistants based on their unique Geico identifiers.
     *
     * @param assistant The assistant to compare with
     * @return `true` if the Geico identifiers match, indicating the same assistant; otherwise, `false`.
     */
    @Override
    public boolean equals(Object assistant) {
        return this.geicoId.getGeicoId().equals(((Assistant) assistant).geicoId.getGeicoId());
    }
}