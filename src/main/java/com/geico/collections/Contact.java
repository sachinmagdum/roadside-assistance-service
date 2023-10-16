package com.geico.collections;

import lombok.Data;

/**
 * The `Contact` class represents contact information including a phone number and an email address.
 */
@Data
public class Contact {
    private String phone; // The phone number
    private String email; // The email address

    /**
     * Constructs a `Contact` object with the specified phone number and email address.
     *
     * @param phone The phone number associated with the contact.
     * @param email The email address associated with the contact.
     */
    public Contact(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }
}