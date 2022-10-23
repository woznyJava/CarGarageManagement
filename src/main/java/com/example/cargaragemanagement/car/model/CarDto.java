package com.example.cargaragemanagement.car.model;

import com.example.cargaragemanagement.car.Fuel;
import com.example.cargaragemanagement.parking.model.Parking;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CarDto {

    private String carBrand;
    private String model;
    private Fuel fuel;
    private boolean parked;
    private boolean deleted;
    private Set<Parking> list;

    public static CarDto fromCar(Car entity) {
        return CarDto.builder()
                .carBrand(entity.getCarBrand())
                .model(entity.getModel())
                .fuel(entity.getFuel())
                .parked(entity.isParked())
                .deleted(entity.isDeleted())
                .list(entity.getListParkings())
                .build();
    }
}