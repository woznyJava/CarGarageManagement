package com.example.cargaragemanagement.car;

import com.example.cargaragemanagement.car.model.Car;
import com.example.cargaragemanagement.car.model.CarUpdateModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Override
    public Car save(Car modelCar) {
        return carRepository.save(new Car(modelCar.getCarBrand(), modelCar.getModel(), modelCar.getPower(), modelCar.getFuel(), modelCar.isHasGas()));
    }

    @Transactional
    @Override
    public void delete(long id) {
        carRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Car findCar(long id) {
        return carRepository.findCarById(id);
    }

    @Transactional
    @Override
    public Car update(CarUpdateModel dto) {
        Car car = findCar(dto.getId());
        CarMapper.update(car, dto);
        return car;
    }
}
