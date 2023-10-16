package com.geico.collections;

import lombok.Data;

@Data
public class Contact {
    private String phone;
    private String email;

    public Contact(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }
}
