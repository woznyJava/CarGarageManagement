package com.example.cargaragemanagement.parking;

import com.example.cargaragemanagement.exception.AddingCarToTheGarageException;
import com.example.cargaragemanagement.parking.model.Parking;
import com.example.cargaragemanagement.request.RequestModel;

public interface ParkingService {

    Parking putTheCarInTheGarage(RequestModel requestModel) throws AddingCarToTheGarageException;

    Parking takeTheCarOut(RequestModel requestModel);

    Parking findById(Long id);
}
