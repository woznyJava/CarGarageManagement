package com.example.cargaragemanagement.garage;

import com.example.cargaragemanagement.garage.model.Garage;
import com.example.cargaragemanagement.garage.model.GarageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/garages")
public class GarageController {
    private final GarageService garageService;

    @PostMapping
    public ResponseEntity<Garage> saveGarage(@RequestBody GarageDto garageDto) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(garageService.save(garageDto));
    }

    @PutMapping
    public ResponseEntity<Garage> updateGarage(@RequestBody GarageDto garageDto) {
        return ResponseEntity.status(HttpStatus.OK).body(garageService.update(garageDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Garage> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(garageService.findGarage(id));
    }
}
