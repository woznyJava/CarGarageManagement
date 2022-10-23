package com.example.cargaragemanagement.garage;

import com.example.cargaragemanagement.garage.model.Garage;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;

public interface GarageRepository extends CrudRepository<Garage, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    Garage findGarageById(long id);
}
