package com.example.cargaragemanagement.garageMapper;

import com.example.cargaragemanagement.garage.model.Garage;
import com.example.cargaragemanagement.garage.model.GarageDto;
import com.example.cargaragemanagement.garage.GarageMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

public class GarageMapperTests {

    @Test
    public void shouldUpdateGarage() {
        Garage garage = new Garage("Test Address", "200", true, new HashSet<>(),  new HashSet<>() );
        GarageDto garageDto2 = new GarageDto("Update", "Update", false);
        GarageMapper.update(garage, garageDto2);

        assertEquals(garage.getAddress(), garageDto2.getAddress());
        assertEquals(garage.getNumberOfParkingSpaces(), garageDto2.getNumberOfParkingSpaces());
        assertEquals(garage.isLpgAllowed(), garageDto2.isLpgAllowed());
    }
}
