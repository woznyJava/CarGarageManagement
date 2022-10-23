package com.example.cargaragemanagement.car;

import com.example.cargaragemanagement.car.model.Car;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;

public interface CarRepository extends CrudRepository<Car, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    Car findCarById(long id);
}
