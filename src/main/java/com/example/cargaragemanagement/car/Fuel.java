package com.example.cargaragemanagement.car;

public enum Fuel {
    PETROL("petrol"),
    DIESEL("diesel"),
    HYBRID("hybrid"),
    ELECTRICIAN("eletrician");

    private final String typeFuel;

    Fuel(String typeFuel) {
        this.typeFuel = typeFuel;
    }

    public String getTypeFuel() {
        return typeFuel;
    }
}