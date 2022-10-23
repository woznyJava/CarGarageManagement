package com.example.cargaragemanagement.garage;

import com.example.cargaragemanagement.garage.model.Garage;
import com.example.cargaragemanagement.garage.model.GarageDto;

public interface GarageMapper {
    static void update(Garage garage, GarageDto garageDto) {

        if (garageDto.getAddress() != null) {
            garage.setAddress(garageDto.getAddress());
        }
        if (garageDto.getNumberOfParkingSpaces() != null) {
            garage.setNumberOfParkingSpaces(garageDto.getNumberOfParkingSpaces());
        }
        if (Boolean.compare(garageDto.isLpgAllowed(), garage.isLpgAllowed()) == 1) {
            garage.setLpgAllowed(garageDto.isLpgAllowed());
        }
        if (Boolean.compare(garageDto.isLpgAllowed(), garage.isLpgAllowed()) == -1) {
            garage.setLpgAllowed(garageDto.isLpgAllowed());
        }
    }
}
