package com.example.cargaragemanagement.parkingService;

import com.example.cargaragemanagement.car.model.Car;
import com.example.cargaragemanagement.car.CarService;
import com.example.cargaragemanagement.car.Fuel;
import com.example.cargaragemanagement.exception.AddingCarToTheGarageException;
import com.example.cargaragemanagement.garage.model.Garage;
import com.example.cargaragemanagement.garage.GarageService;
import com.example.cargaragemanagement.parking.model.Parking;
import com.example.cargaragemanagement.parking.ParkingRepository;
import com.example.cargaragemanagement.parking.ParkingServiceImpl;
import com.example.cargaragemanagement.request.RequestModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTests {

    @InjectMocks
    private ParkingServiceImpl parkingServiceImpl;

    @Mock
    private CarService carService;

    @Mock
    private GarageService garageService;

    @Mock
    private ParkingRepository parkingRepository;

    @Test
    public void shouldPutTheCarInTheGarage() throws AddingCarToTheGarageException {
        Set<Parking> set = new HashSet<>();
        RequestModel requestModel = new RequestModel(1L, 1L);
        Garage garage = new Garage("Test Address", "0", true, new HashSet<>(),  new HashSet<>());
        Car car = new Car(1L, "Test", "Test", "200", Fuel.PETROL, null, set, false, false, false);

        when(carService.findCar(anyLong())).thenReturn(car);
        when(garageService.findGarage(anyLong())).thenReturn(garage);
        when(garageService.checkPossibilityAddingCarToGarage(requestModel)).thenReturn(garage);

        Parking parking = parkingServiceImpl.putTheCarInTheGarage(requestModel);

        assertEquals(parking.getCar(), car);
        assertEquals(parking.getGarage(), garage);
        assertTrue(car.getListParkings().contains(parking));
        assertTrue(car.isParked());

    }


    @Test
    public void shouldTakeCarOut() {
        RequestModel requestModel = new RequestModel(1L, 1L);
        Set<Parking> set = new HashSet<>();
        Car car = new Car(1L, "Test", "Test", "200", Fuel.PETROL, null, set, false, false, false);
        Garage garage = new Garage("Test Address", "0", true, new HashSet<>(),  new HashSet<>());
        Parking parking = new Parking(1L, garage, car, LocalDateTime.of(1999, 11, 2, 3, 4, 1), LocalDateTime.of(2000, 10, 15, 20, 10, 1));

        when(carService.findCar(anyLong())).thenReturn(car);
        when(parkingRepository.findParkingById(anyLong())).thenReturn(parking);

        parkingServiceImpl.takeTheCarOut(requestModel);

        assertFalse(car.isParked());
        assertEquals(parking.getGarage(), garage);
        assertEquals(parking.getCar(), car);
        assertEquals(parking.getArrivalDate(), LocalDateTime.of(1999, 11, 2, 3, 4, 1));
    }

    @Test
    public void shouldFindParkingById() {
        Set<Parking> set = new HashSet<>();
        Car car = new Car(1L, "Test", "Test", "200", Fuel.PETROL, null, set, false, false, false);
        Garage garage = new Garage("Test Address", "0", true, new HashSet<>(),  new HashSet<>());
        Parking parking = new Parking(1L, garage, car, LocalDateTime.of(1999, 11, 2, 3, 4, 1),
                LocalDateTime.of(2000, 10, 15, 20, 10, 1));

        when(parkingRepository.findParkingById(anyLong())).thenReturn(parking);

        Parking parking2 = parkingServiceImpl.findById(1L);

        assertEquals(parking.getGarage(), parking2.getGarage());
        assertEquals(parking.getCar(), parking2.getCar());
        assertEquals(parking.getArrivalDate(), parking2.getArrivalDate());
        assertFalse(car.isParked());
    }
}
