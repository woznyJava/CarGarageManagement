package com.example.cargaragemanagement.garage.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GarageDto {
    private long id;
    private String address;
    private String numberOfParkingSpaces;
    private boolean lpgAllowed;

    public GarageDto(long id, String address, String numberOfParkingSpaces, boolean lpgAllowed) {
        this.id = id;
        this.address = address;
        this.numberOfParkingSpaces = numberOfParkingSpaces;
        this.lpgAllowed = lpgAllowed;
    }

    public GarageDto(String address, String numberOfParkingSpaces, boolean lpgAllowed) {
        this.address = address;
        this.numberOfParkingSpaces = numberOfParkingSpaces;
        this.lpgAllowed = lpgAllowed;
    }
}
