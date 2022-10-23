package com.example.cargaragemanagement.parking.model;

import com.example.cargaragemanagement.car.model.Car;
import com.example.cargaragemanagement.garage.model.Garage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Garage garage;

    @ManyToOne
    private Car car;

    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;

    public Parking(Garage garage, Car car, LocalDateTime arrivalDate) {
        this.garage = garage;
        this.car = car;
        this.arrivalDate = arrivalDate;
    }
}
