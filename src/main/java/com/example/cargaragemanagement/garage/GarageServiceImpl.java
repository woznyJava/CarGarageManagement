package com.example.cargaragemanagement.garage;

import com.example.cargaragemanagement.car.model.Car;
import com.example.cargaragemanagement.car.CarService;
import com.example.cargaragemanagement.exception.AddingCarToTheGarageException;
import com.example.cargaragemanagement.garage.model.Garage;
import com.example.cargaragemanagement.garage.model.GarageDto;
import com.example.cargaragemanagement.request.RequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@RequiredArgsConstructor

public class GarageServiceImpl implements GarageService {
    private final GarageRepository garageRepository;
    private final CarService carService;

    @Override
    public Garage save(GarageDto garageDto) {
        return garageRepository.save(new Garage(garageDto.getAddress(), garageDto.getNumberOfParkingSpaces(), garageDto.isLpgAllowed(), new HashSet<>(), new HashSet<>()));
    }

    @Transactional
    @Override
    public Garage findGarage(long id) {
        return garageRepository.findGarageById(id);
    }

    @Transactional
    @Override
    public Garage update(GarageDto garageDto) {
        Garage garage = findGarage(garageDto.getId());
        GarageMapper.update(garage, garageDto);
        return garage;
    }

    @Transactional
    @Override
    public Garage checkPossibilityAddingCarToGarage(RequestModel requestModel) throws AddingCarToTheGarageException {
        Garage garage = findGarage(requestModel.getIdGarageParking());
        Car car = carService.findCar(requestModel.getIdCar());

        if (checkAvailabilityOfSpace(requestModel.getIdGarageParking())) {
            throw new AddingCarToTheGarageException("Garage is full, car cannot be added.");
        }
        if (car.isParked()) {
            throw new AddingCarToTheGarageException("The car is already added to the garage");
        }
        if (car.isHasGas() || !garage.isLpgAllowed()) {
            throw new AddingCarToTheGarageException("The garage does not allow cars with gas");
        }
        return garage;
    }

    @Transactional
    @Override
    public boolean checkAvailabilityOfSpace(Long idGarage) {
        Garage garage = garageRepository.findGarageById(idGarage);
        int size = Integer.parseInt(garage.getNumberOfParkingSpaces());
        int usedSize = garage.getCarList().size();
        if (size > usedSize) {
            return false;
        } else if (size == usedSize + 1) {
            return false;
        }
        return true;
    }
}
