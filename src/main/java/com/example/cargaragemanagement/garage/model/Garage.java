package com.example.cargaragemanagement.garage.model;

import com.example.cargaragemanagement.car.model.Car;
import com.example.cargaragemanagement.parking.model.Parking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Address cannot be null or empty")
    private String address;

    @NotBlank(message = "Numer of parking spaces cannot be null or empty")
    private String numberOfParkingSpaces;
    private boolean lpgAllowed;

    @OneToMany(mappedBy = "garage")
    private Set<Car> carList;

    @OneToMany(mappedBy = "garage")
    private Set<Parking> listParkings;

    public Garage(String address, String numberOfParkingSpaces, boolean lpgAllowed, Set<Car> carList, Set<Parking> listParkings) {
        this.address = address;
        this.numberOfParkingSpaces = numberOfParkingSpaces;
        this.lpgAllowed = lpgAllowed;
        this.carList = carList;
        this.listParkings = listParkings;
    }

    public void addParkingToList(Parking parking) {
        listParkings.add(parking);
    }
}
