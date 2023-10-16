package com.geico.roadsideassistance.business.objects;

import com.geico.collections.Contact;
import com.geico.collections.Geolocation;
import com.geico.collections.Name;
import lombok.Data;

/**
 * The `Customer` class represents a Geico customer, containing information about the customer's name,
 * contact details, location, and a unique customer identifier.
 */
@Data
public class Customer {
    private Name name;               // The customer's name
    private Contact contact;         // The customer's contact information
    private Geolocation location;    // The customer's geolocation (location on a map)
    private GeicoIdentifier customerId; // A unique identifier associated with the customer

    /**
     * Constructor to create a new `Customer` instance with the provided details.
     *
     * @param name          The name of the customer
     * @param contact       The contact information of the customer
     * @param location      The geolocation representing the customer's location
     * @param customerId    A unique identifier associated with the customer
     */
    public Customer(Name name, Contact contact, Geolocation location, GeicoIdentifier customerId) {
        this.name = name;
        this.contact = contact;
        this.location = location;
        this.customerId = customerId;
    }
}
