package com.example.cargaragemanagement.garageService;

import com.example.cargaragemanagement.car.*;
import com.example.cargaragemanagement.car.model.Car;
import com.example.cargaragemanagement.exception.AddingCarToTheGarageException;
import com.example.cargaragemanagement.garage.model.Garage;
import com.example.cargaragemanagement.garage.model.GarageDto;
import com.example.cargaragemanagement.garage.GarageRepository;
import com.example.cargaragemanagement.garage.GarageServiceImpl;
import com.example.cargaragemanagement.request.RequestModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GarageServiceTests {

    @InjectMocks
    private GarageServiceImpl garageService;

    @Mock
    private GarageRepository garageRepository;

    @Mock
    private CarService carService;


    @Test
    public void shouldSaveAndFindGarage() {
        Garage garage = new Garage("Test Ulica Test Miasto", "200", true, new HashSet<>(),  new HashSet<>());
        GarageDto modelGarage = new GarageDto("Test Ulica Test Miasto", "200", true);

        when(garageRepository.findGarageById(anyLong())).thenReturn(garage);
        garageService.save(modelGarage);
        Garage garage2 = garageService.findGarage(anyLong());

        assertEquals(garage.getAddress(), garage2.getAddress());
        assertEquals(garage.getNumberOfParkingSpaces(), garage2.getNumberOfParkingSpaces());
        assertEquals(garage.isLpgAllowed(), garage2.isLpgAllowed());
    }

    @Test
    public void shouldUpdateGarage() {
        Garage garage = new Garage("Test Address", "200", true, new HashSet<>(),  new HashSet<>());
        GarageDto garageDto2 = new GarageDto("Update", "Update", false);
        when(garageRepository.findGarageById(anyLong())).thenReturn(garage);
        Garage garage2 = garageService.update(garageDto2);

        assertEquals(garage2.getAddress(), garageDto2.getAddress());
        assertEquals(garage2.getNumberOfParkingSpaces(), garageDto2.getNumberOfParkingSpaces());
        assertEquals(garage2.isLpgAllowed(), garageDto2.isLpgAllowed());
    }

    @Test
    public void shouldReturnGarageAfterCheckPossibilityAddingCarToGarage() throws AddingCarToTheGarageException {
        RequestModel requestModel = new RequestModel(1L, 1L);
        Garage garage = new Garage("Test Address", "200", true, new HashSet<>(),  new HashSet<>());
        Car car = new Car("Test", "Test", "Test", Fuel.ELECTRICIAN, false);

        when(garageRepository.findGarageById(anyLong())).thenReturn(garage);
        when(carService.findCar(anyLong())).thenReturn(car);
        Garage garage2 = garageService.checkPossibilityAddingCarToGarage(requestModel);
        assertEquals(garage.getAddress(), garage2.getAddress());
        assertEquals(garage.getNumberOfParkingSpaces(), garage2.getNumberOfParkingSpaces());
        assertEquals(garage.isLpgAllowed(), garage2.isLpgAllowed());
    }

    @Test
    public void shouldCheckAvailabilityOfSpace() {
        Garage garage = new Garage("Test Address", "200", true, Collections.emptySet(),  new HashSet<>());
        when(garageRepository.findGarageById(anyLong())).thenReturn(garage);
        assertFalse(garageService.checkAvailabilityOfSpace(1L));
    }

    @Test
    public void shouldCheckAvailabilityOfSpace2() {
        Garage garage = new Garage("Test Address", "0", true, new HashSet<>(),  new HashSet<>());
        when(garageRepository.findGarageById(anyLong())).thenReturn(garage);
        assertTrue(garageService.checkAvailabilityOfSpace(1L));
    }

    @Test
    public void shouldThrowExceptionGarageIsFull() throws AddingCarToTheGarageException {
        RequestModel requestModel = new RequestModel(1L, 1L);
        Car car = new Car("Test", "Test", "Test", Fuel.ELECTRICIAN, false);
        Garage garage = new Garage("Test Address", "0", false, new HashSet<>(),  new HashSet<>());

        when(garageRepository.findGarageById(anyLong())).thenReturn(garage);
        when(carService.findCar(anyLong())).thenReturn(car);
        assertThrows(AddingCarToTheGarageException.class, () -> garageService.checkPossibilityAddingCarToGarage(requestModel), "Garage is full, car cannot be added.");
    }

    @Test
    public void shouldThrowExceptionGarageDoesNotAcceptGas() throws AddingCarToTheGarageException {
        RequestModel requestModel = new RequestModel(1L, 1L);
        Car car = new Car("Test", "Test", "Test", Fuel.ELECTRICIAN, true);
        Garage garage = new Garage("Test Address", "0", false, new HashSet<>(),  new HashSet<>());

        when(garageRepository.findGarageById(anyLong())).thenReturn(garage);
        when(carService.findCar(anyLong())).thenReturn(car);
        assertThrows(AddingCarToTheGarageException.class, () -> garageService.checkPossibilityAddingCarToGarage(requestModel), "The garage does not allow cars with gas");
    }

    @Test
    public void shouldThrowExceptionCarIsAlreadyAdded() throws AddingCarToTheGarageException {
        RequestModel requestModel = new RequestModel(1L, 1L);
        Garage garage = new Garage("Test Address", "200", true, new HashSet<>(),  new HashSet<>());
        Car car = new Car(1L,"Test", "Test", "Test", Fuel.ELECTRICIAN,garage, new HashSet<>(), false, true,false);

        when(garageRepository.findGarageById(anyLong())).thenReturn(garage);
        when(carService.findCar(anyLong())).thenReturn(car);

        assertThrows(AddingCarToTheGarageException.class, () -> garageService.checkPossibilityAddingCarToGarage(requestModel), "The car is already added to the garage");
    }
}



