package com.example.cargaragemanagement.parking;

import com.example.cargaragemanagement.exception.AddingCarToTheGarageException;
import com.example.cargaragemanagement.parking.model.ParkingDto;
import com.example.cargaragemanagement.parking.model.ParkingCreatedReturnDto;
import com.example.cargaragemanagement.request.RequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parkings")
public class ParkingController {
    private final ParkingService parkingService;

    @PostMapping
    public ResponseEntity<ParkingCreatedReturnDto> putTheCar(@RequestBody RequestModel requestModel) throws AddingCarToTheGarageException {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(ParkingCreatedReturnDto.fromParking2(parkingService.putTheCarInTheGarage(requestModel)));
    }

    @PutMapping
    public ResponseEntity<ParkingDto> exhibitTheCar(@RequestBody RequestModel requestModel) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(ParkingDto.fromParking(parkingService.takeTheCarOut(requestModel)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDto> findParkingById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(ParkingDto.fromParking(parkingService.findById(id)));
    }
}
