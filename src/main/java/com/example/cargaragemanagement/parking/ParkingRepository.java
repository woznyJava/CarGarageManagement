package com.example.cargaragemanagement.parking;

import com.example.cargaragemanagement.parking.model.Parking;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;

public interface ParkingRepository extends CrudRepository<Parking, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    Parking findParkingById(Long id);
}
