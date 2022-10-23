package com.example.cargaragemanagement.carMapper;

import com.example.cargaragemanagement.car.model.Car;
import com.example.cargaragemanagement.car.model.CarUpdateModel;
import com.example.cargaragemanagement.car.CarMapper;
import com.example.cargaragemanagement.car.Fuel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
public class CarMapperTest {

    @Test
    public void shouldUpdateCar() {
        CarUpdateModel modelCar2 = new CarUpdateModel(1L, "Update", "Update", "200", Fuel.PETROL, false);
        Car car = new Car(1L, "Test", "Test", "200", Fuel.PETROL, null, new HashSet<>(), false, false, false);
        CarMapper.update(car, modelCar2);

        assertEquals(car.getCarBrand(), modelCar2.getCarBrand());
        assertEquals(car.getFuel(), modelCar2.getFuel());
        assertEquals(car.getModel(), modelCar2.getModel());
        assertEquals(car.isHasGas(), modelCar2.isHasGas());
    }
}
