package com.example.cargaragemanagement.car.model;

import com.example.cargaragemanagement.car.Fuel;
import com.example.cargaragemanagement.garage.model.Garage;
import com.example.cargaragemanagement.parking.model.Parking;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE car SET deleted = 'true' WHERE id = ?", check = ResultCheckStyle.COUNT)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The Car brand cannot be null or empty")
    @Pattern(regexp = "[A-Z][a-z]{1,20}", message = "The Car brand does not fit the pattern")
    private String carBrand;

    @NotBlank(message = "Model cannot be null or empty")
    @Pattern(regexp = "[A-Z][a-z]{1,20}", message = "Model does not fit the pattern")
    private String model;

    @NotBlank(message = "Power cannot be null or empty")
    private String power;

    @Enumerated(EnumType.STRING)
    private Fuel fuel;

    @JsonIgnore
    @ManyToOne
    private Garage garage;

    @OneToMany(mappedBy = "car")
    private Set<Parking> listParkings;

    private boolean hasGas;

    private boolean parked;

    private boolean deleted;

    public Car(String carBrand, String model, String power, Fuel fuel, boolean hasGas) {
        this.carBrand = carBrand;
        this.model = model;
        this.power = power;
        this.fuel = fuel;
        this.hasGas = hasGas;
    }

    public void addParkingToList(Parking parking) {
        listParkings.add(parking);
    }
}
