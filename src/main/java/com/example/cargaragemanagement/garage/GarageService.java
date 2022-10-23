package com.example.cargaragemanagement.garage;

import com.example.cargaragemanagement.exception.AddingCarToTheGarageException;
import com.example.cargaragemanagement.garage.model.Garage;
import com.example.cargaragemanagement.garage.model.GarageDto;
import com.example.cargaragemanagement.request.RequestModel;

public interface GarageService {

    Garage save(GarageDto garageDto);

    Garage findGarage(long id);

    Garage update(GarageDto garageDto);

    Garage checkPossibilityAddingCarToGarage(RequestModel requestModel) throws AddingCarToTheGarageException;

    boolean checkAvailabilityOfSpace(Long idGarage);

}
