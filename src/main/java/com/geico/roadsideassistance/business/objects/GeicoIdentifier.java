package com.geico.roadsideassistance.business.objects;

import lombok.Data;

@Data
public class GeicoIdentifier {

    private String geicoId;

    public GeicoIdentifier(String geicoId) {
        this.geicoId = geicoId;
    }
}
