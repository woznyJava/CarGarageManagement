package com.example.cargaragemanagement.car;

import com.example.cargaragemanagement.car.model.Car;
import com.example.cargaragemanagement.car.model.CarUpdateModel;

public interface CarMapper {

    static void update(Car car, CarUpdateModel carUpdateModel) {
        if (carUpdateModel.getCarBrand() != null) {
            car.setCarBrand(carUpdateModel.getCarBrand());
        }
        if (carUpdateModel.getModel() != null) {
            car.setModel(carUpdateModel.getModel());
        }
        if (carUpdateModel.getFuel() != null) {
            car.setFuel(carUpdateModel.getFuel());
        }
        if (carUpdateModel.getPower() != null) {
            car.setPower(carUpdateModel.getPower());
        }
        if (carUpdateModel.isParked()) {
            car.setParked(true);
        }
        if (!carUpdateModel.isHasGas() == car.isHasGas()) {
            car.setHasGas(carUpdateModel.isHasGas());
        }
    }
}
