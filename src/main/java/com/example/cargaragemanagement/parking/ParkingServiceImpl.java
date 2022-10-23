package com.example.cargaragemanagement.parking;

import com.example.cargaragemanagement.car.model.Car;
import com.example.cargaragemanagement.car.CarService;
import com.example.cargaragemanagement.exception.AddingCarToTheGarageException;
import com.example.cargaragemanagement.garage.model.Garage;
import com.example.cargaragemanagement.garage.GarageService;
import com.example.cargaragemanagement.parking.model.Parking;
import com.example.cargaragemanagement.request.RequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParkingServiceImpl implements ParkingService {
    private final GarageService garageService;
    private final CarService carService;
    private final ParkingRepository parkingRepository;

    @Transactional
    @Override
    public Parking putTheCarInTheGarage(RequestModel requestModel) throws AddingCarToTheGarageException {
        Garage garage = garageService.findGarage(requestModel.getIdGarageParking());
        Car car = carService.findCar(requestModel.getIdCar());
        garageService.checkPossibilityAddingCarToGarage(requestModel);
        Parking parking = new Parking(garage, car, LocalDateTime.now());

        car.addParkingToList(parking);
        car.setParked(true);
        garage.addParkingToList(parking);
        garage.getCarList().add(car);
        parkingRepository.save(parking);
        car.setGarage(garage);
        return parking;
    }

    @Transactional
    @Override
    public Parking takeTheCarOut(RequestModel requestModel) {
        Car car = carService.findCar(requestModel.getIdCar());
        Parking parking = parkingRepository.findParkingById(requestModel.getIdGarageParking());
        parking.setDepartureDate(LocalDateTime.now());
        car.setParked(false);
        car.setGarage(null);
        return parking;
    }

    @Transactional
    @Override
    public Parking findById(Long id) {
        return parkingRepository.findParkingById(id);
    }
}
