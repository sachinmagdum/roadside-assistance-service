package com.geico.roadsideassistance.business.objects;

import com.geico.collections.Contact;
import com.geico.collections.Geolocation;
import com.geico.collections.Name;
import lombok.Data;

@Data
public class Customer {
    private Name name;
    private Contact contact;
    private Geolocation location;

    private GeicoIdentifier customerId;

    public Customer(Name name, Contact contact, Geolocation location, GeicoIdentifier customerId) {
        this.name = name;
        this.contact = contact;
        this.location = location;
        this.customerId = customerId;
    }
}
