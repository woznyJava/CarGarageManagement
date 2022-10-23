package com.example.cargaragemanagement.car;

import com.example.cargaragemanagement.car.model.Car;
import com.example.cargaragemanagement.car.model.CarUpdateModel;
import com.example.cargaragemanagement.car.model.CarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cars")
public class CarController {
    private final CarService carService;

    @PostMapping
    public ResponseEntity<Car> saveCar(@RequestBody Car modelCar) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(carService.save(modelCar));
    }

    @PutMapping
    public ResponseEntity<Car> updateCar(@RequestBody CarUpdateModel modelCar) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.update(modelCar));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable Long id) {
        carService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(CarDto.fromCar(carService.findCar(id)));
    }
}
