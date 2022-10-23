package com.example.cargaragemanagement.carService;

import com.example.cargaragemanagement.car.*;
import com.example.cargaragemanagement.car.model.Car;
import com.example.cargaragemanagement.car.model.CarUpdateModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTests {

    @InjectMocks
    private CarServiceImpl carServicel;

    @Mock
    private CarRepository carRepository;

    @Test
    public void shouldSaveAndFindCar() {
        Car car = new Car("Test", "Test", "Test,", Fuel.DIESEL, false);
        Car modelCar = new Car("Test", "Test", "Test", Fuel.DIESEL, false);

        when(carRepository.findCarById(anyLong())).thenReturn(car);
        carServicel.save(modelCar);
        Car car2 = carServicel.findCar(1);

        assertEquals(car.getCarBrand(), car2.getCarBrand());
        assertEquals(car.getFuel(), car2.getFuel());
        assertEquals(car.getPower(), car2.getPower());
        assertEquals(car.getModel(), car2.getModel());
        assertEquals(car.isHasGas(), car2.isHasGas());
    }

    @Test
    public void shouldEditDoctor() {
        Car car = new Car("Test", "Test", "Test", Fuel.ELECTRICIAN, true);
        CarUpdateModel modelCar2 = new CarUpdateModel(1L, "Update", "Update", "200", Fuel.PETROL, false);
        when(carRepository.findCarById(anyLong())).thenReturn(car);
        Car car2 = carServicel.update(modelCar2);

        assertEquals(modelCar2.getModel(), car2.getModel());
        assertEquals(modelCar2.getFuel(), car2.getFuel());
        assertEquals(modelCar2.getPower(), car2.getPower());
        assertEquals(modelCar2.getCarBrand(), car2.getCarBrand());
    }
}
