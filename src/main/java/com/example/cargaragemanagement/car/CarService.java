package com.example.cargaragemanagement.car;

import com.example.cargaragemanagement.car.model.Car;
import com.example.cargaragemanagement.car.model.CarUpdateModel;

public interface CarService {

    Car save(Car modelCar);

    void delete(long id);

    Car findCar(long id);

    Car update(CarUpdateModel modelCar);
}
