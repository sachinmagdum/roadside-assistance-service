package com.geico.collections;

public enum DistanceUnit {
    KILOMETERS,
    MILES;

    @Override
    public String toString() {
        // Customize the display of the enum values if needed
        switch (this) {
            case KILOMETERS:
                return "km";
            case MILES:
                return "miles";
            default:
                return "unknown";
        }
    }
}
