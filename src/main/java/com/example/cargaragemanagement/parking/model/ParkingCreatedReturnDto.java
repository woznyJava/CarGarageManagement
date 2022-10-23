package com.example.cargaragemanagement.parking.model;

import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class ParkingCreatedReturnDto {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Long id;
    private String garageDto;
    private String carDto;
    private String arrivalDate;
    private String departureDate;

    public static ParkingCreatedReturnDto fromParking2(Parking entity) {
        return ParkingCreatedReturnDto.builder()
                .id((entity.getId()))
                .garageDto(entity.getGarage().getAddress())
                .carDto(entity.getCar().getCarBrand() + " " + entity.getCar().getModel())
                .arrivalDate((entity.getArrivalDate().format(formatter)))
                .build();
    }
}
