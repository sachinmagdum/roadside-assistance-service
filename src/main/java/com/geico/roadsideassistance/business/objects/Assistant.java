package com.geico.roadsideassistance.business.objects;

import com.geico.collections.Contact;
import com.geico.collections.Geolocation;
import com.geico.collections.Name;
import lombok.Data;

@Data
public class Assistant {
    private Geolocation geolocation;
    private Status status;
    private Name name;
    private Contact contact;

    private GeicoIdentifier geicoId;

    public enum Status {
        RESERVED,
        AVAILABLE,
        OFF_DUTY
    }

    public Assistant(Geolocation geolocation, Name name, Contact contact, GeicoIdentifier geicoId) {
        this.geolocation = geolocation;
        this.status = Status.AVAILABLE; // By default, assistants are available
        this.name = name;
        this.contact = contact;
        this.geicoId = geicoId;
    }

    @Override
    public boolean equals(Object assistant) {
        return this.geicoId.getGeicoId().equals(((Assistant) assistant).geicoId.getGeicoId());
    }
}