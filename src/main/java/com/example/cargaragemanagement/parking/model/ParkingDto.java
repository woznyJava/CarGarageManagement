package com.example.cargaragemanagement.parking.model;

import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class ParkingDto {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private String id;
    private String garageDto;
    private String carDto;
    private String arrivalDate;
    private String departureDate;

    public static ParkingDto fromParking(Parking entity) {
        return ParkingDto.builder()
                .id(String.valueOf(entity.getId()))
                .garageDto(entity.getGarage().getAddress())
                .carDto(entity.getCar().getId() + " " + entity.getCar().getCarBrand() + " " + entity.getCar().getModel())
                .arrivalDate(entity.getArrivalDate().format(formatter))
                .departureDate(entity.getDepartureDate().format(formatter))
                .build();
    }
}
