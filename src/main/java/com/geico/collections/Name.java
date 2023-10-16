package com.geico.collections;

import lombok.Data;

/**
 * The `Name` class represents a person's first name and last name.
 */
@Data
public class Name {
    private String firstName; // The first name of the person.
    private String lastName;  // The last name of the person.

    /**
     * Initializes a `Name` object with the given first name and last name.
     *
     * @param firstName The first name of the person.
     * @param lastName  The last name of the person.
     */
    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
