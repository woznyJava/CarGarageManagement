package com.example.cargaragemanagement.car.model;

import com.example.cargaragemanagement.car.Fuel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarUpdateModel {

    private Long id;
    private String carBrand;
    private String model;
    private String power;
    private Fuel fuel;
    private boolean hasGas;
    private boolean parked;

    public CarUpdateModel(long id, String carBrand, String model, String power, Fuel fuel, boolean hasGas) {
        this.id = id;
        this.carBrand = carBrand;
        this.model = model;
        this.power = power;
        this.fuel = fuel;
        this.hasGas = hasGas;
    }
}
