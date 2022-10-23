package com.example.cargaragemanagement.parkingController;

import com.example.cargaragemanagement.car.model.Car;
import com.example.cargaragemanagement.car.Fuel;
import com.example.cargaragemanagement.garage.model.Garage;
import com.example.cargaragemanagement.garage.model.GarageDto;
import com.example.cargaragemanagement.parking.model.ParkingCreatedReturnDto;
import com.example.cargaragemanagement.request.RequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ParkingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldPutCar() throws Exception {

        Car modelCar = new Car("Test", "Test", "Test", Fuel.PETROL, false);

        Car savedCar = objectMapper.readValue(mockMvc.perform(post("http://localhost:8000/api/v1/cars")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelCar)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carBrand").value(modelCar.getCarBrand()))
                .andExpect(jsonPath("$.model").value(modelCar.getModel()))
                .andExpect(jsonPath("$.power").value(modelCar.getPower()))
                .andExpect(jsonPath("$.fuel").value(modelCar.getFuel().toString()))
                .andReturn().getResponse().getContentAsByteArray(), Car.class);

        GarageDto modelGarage = new GarageDto("Test Ulica Test Miasto", "200", true);

        Garage savedGarage = objectMapper.readValue(mockMvc.perform(post("http://localhost:8000/api/v1/garages")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelGarage)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value(modelGarage.getAddress()))
                .andExpect(jsonPath("$.numberOfParkingSpaces").value(modelGarage.getNumberOfParkingSpaces()))
                .andExpect(jsonPath("$.lpgAllowed").value(modelGarage.isLpgAllowed()))
                .andReturn().getResponse().getContentAsByteArray(), Garage.class);

        RequestModel requestModel = new RequestModel(savedCar.getId(), savedGarage.getId());
        Gson gson = new Gson();
        String json = gson.toJson(requestModel);

        mockMvc.perform(post("http://localhost:8000/api/v1/parkings")
                        .contentType(APPLICATION_JSON)
                        .content(String.valueOf(json)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carDto").value("Test Test"))
                .andExpect(jsonPath("$.garageDto").value("Test Ulica Test Miasto"));
    }

    @Test
    public void shouldTakeTheCarOut() throws Exception {

        Car modelCar = new Car("Lexus", "Rc", "250", Fuel.PETROL, false);

        Car savedCar = objectMapper.readValue(mockMvc.perform(post("http://localhost:8000/api/v1/cars")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelCar)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carBrand").value(modelCar.getCarBrand()))
                .andExpect(jsonPath("$.model").value(modelCar.getModel()))
                .andExpect(jsonPath("$.power").value(modelCar.getPower()))
                .andExpect(jsonPath("$.fuel").value(modelCar.getFuel().toString()))
                .andReturn().getResponse().getContentAsByteArray(), Car.class);

        GarageDto modelGarage = new GarageDto("Korona Kielce, Widelce", "200", true);

        Garage savedGarage = objectMapper.readValue(mockMvc.perform(post("http://localhost:8000/api/v1/garages")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelGarage)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value(modelGarage.getAddress()))
                .andExpect(jsonPath("$.numberOfParkingSpaces").value(modelGarage.getNumberOfParkingSpaces()))
                .andExpect(jsonPath("$.lpgAllowed").value(modelGarage.isLpgAllowed()))
                .andReturn().getResponse().getContentAsByteArray(), Garage.class);

        RequestModel requestModel = new RequestModel(savedCar.getId(), savedGarage.getId());

        ParkingCreatedReturnDto parkingDto2 = objectMapper.readValue(mockMvc.perform(post("http://localhost:8000/api/v1/parkings")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestModel)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carDto").value("Lexus Rc"))
                .andExpect(jsonPath("$.garageDto").value("Korona Kielce, Widelce"))
                .andReturn().getResponse().getContentAsByteArray(), ParkingCreatedReturnDto.class);

        RequestModel requestModel2 = new RequestModel(savedCar.getId(), parkingDto2.getId());
        Gson gson = new Gson();
        String json = gson.toJson(requestModel2);

        mockMvc.perform(put("http://localhost:8000/api/v1/parkings")
                        .contentType(APPLICATION_JSON)
                        .content(String.valueOf(json)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carDto").value(savedCar.getId() + " Lexus Rc"))
                .andExpect(jsonPath("$.garageDto").value("Korona Kielce, Widelce"));
    }

    @Test
    public void shouldFindParking() throws Exception {

        Car modelCar = new Car("Testcar", "Testmodel", "250", Fuel.PETROL, false);

        Car savedCar = objectMapper.readValue(mockMvc.perform(post("http://localhost:8000/api/v1/cars")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelCar)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carBrand").value(modelCar.getCarBrand()))
                .andExpect(jsonPath("$.model").value(modelCar.getModel()))
                .andExpect(jsonPath("$.power").value(modelCar.getPower()))
                .andExpect(jsonPath("$.fuel").value(modelCar.getFuel().toString()))
                .andReturn().getResponse().getContentAsByteArray(), Car.class);


        GarageDto modelGarage = new GarageDto("Test address", "200", true);

        Garage savedGarage = objectMapper.readValue(mockMvc.perform(post("http://localhost:8000/api/v1/garages")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelGarage)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value(modelGarage.getAddress()))
                .andExpect(jsonPath("$.numberOfParkingSpaces").value(modelGarage.getNumberOfParkingSpaces()))
                .andExpect(jsonPath("$.lpgAllowed").value(modelGarage.isLpgAllowed()))
                .andReturn().getResponse().getContentAsByteArray(), Garage.class);

        RequestModel requestModel = new RequestModel(savedCar.getId(), savedGarage.getId());

        ParkingCreatedReturnDto parkingDto2 = objectMapper.readValue(mockMvc.perform(post("http://localhost:8000/api/v1/parkings")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestModel)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carDto").value("Testcar Testmodel"))
                .andExpect(jsonPath("$.garageDto").value("Test address"))
                .andReturn().getResponse().getContentAsByteArray(), ParkingCreatedReturnDto.class);

        RequestModel requestModel2 = new RequestModel(savedCar.getId(), parkingDto2.getId());
        Gson gson = new Gson();
        String json = gson.toJson(requestModel2);

        mockMvc.perform(put("http://localhost:8000/api/v1/parkings")
                        .contentType(APPLICATION_JSON)
                        .content(String.valueOf(json)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carDto").value(savedCar.getId() + " Testcar Testmodel"))
                .andExpect(jsonPath("$.garageDto").value("Test address"));

        mockMvc.perform(get("http://localhost:8000/api/v1/parkings/" + parkingDto2.getId())
                        .contentType(APPLICATION_JSON)
                        .content(String.valueOf(json)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carDto").value(savedCar.getId() + " Testcar Testmodel"))
                .andExpect(jsonPath("$.garageDto").value("Test address"));
    }


    @Test
    public void shouldThrowExceptionGarageIsFull() throws Exception {

        Car modelCar = new Car("Test", "Test", "Test", Fuel.PETROL, false);

        Car savedCar = objectMapper.readValue(mockMvc.perform(post("http://localhost:8000/api/v1/cars")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelCar)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carBrand").value(modelCar.getCarBrand()))
                .andExpect(jsonPath("$.model").value(modelCar.getModel()))
                .andExpect(jsonPath("$.power").value(modelCar.getPower()))
                .andExpect(jsonPath("$.fuel").value(modelCar.getFuel().toString()))
                .andReturn().getResponse().getContentAsByteArray(), Car.class);

        GarageDto modelGarage = new GarageDto("Test Ulica Test Miasto", "0", true);

        Garage savedGarage = objectMapper.readValue(mockMvc.perform(post("http://localhost:8000/api/v1/garages")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelGarage)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value(modelGarage.getAddress()))
                .andExpect(jsonPath("$.numberOfParkingSpaces").value(modelGarage.getNumberOfParkingSpaces()))
                .andExpect(jsonPath("$.lpgAllowed").value(modelGarage.isLpgAllowed()))
                .andReturn().getResponse().getContentAsByteArray(), Garage.class);

        RequestModel requestModel = new RequestModel(savedCar.getId(), savedGarage.getId());

        mockMvc.perform(post("http://localhost:8000/api/v1/parkings")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestModel)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Garage is full, car cannot be added."));
    }

    @Test
    public void shouldThrowExceptionCarIsAlreadyAdded() throws Exception {

        Car modelCar = new Car("Test", "Test", "Test", Fuel.PETROL, false);

        Car savedCar = objectMapper.readValue(mockMvc.perform(post("http://localhost:8000/api/v1/cars")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelCar)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carBrand").value(modelCar.getCarBrand()))
                .andExpect(jsonPath("$.model").value(modelCar.getModel()))
                .andExpect(jsonPath("$.power").value(modelCar.getPower()))
                .andExpect(jsonPath("$.fuel").value(modelCar.getFuel().toString()))
                .andReturn().getResponse().getContentAsByteArray(), Car.class);

        GarageDto modelGarage = new GarageDto("Test Ulica Test Miasto", "200", true);

        Garage savedGarage = objectMapper.readValue(mockMvc.perform(post("http://localhost:8000/api/v1/garages")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelGarage)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value(modelGarage.getAddress()))
                .andExpect(jsonPath("$.numberOfParkingSpaces").value(modelGarage.getNumberOfParkingSpaces()))
                .andExpect(jsonPath("$.lpgAllowed").value(modelGarage.isLpgAllowed()))
                .andReturn().getResponse().getContentAsByteArray(), Garage.class);

        RequestModel requestModel = new RequestModel(savedCar.getId(), savedGarage.getId());

        mockMvc.perform(post("http://localhost:8000/api/v1/parkings")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestModel)))
                .andDo(print())
                .andExpect(status().isCreated());

        RequestModel requestModel2 = new RequestModel(savedCar.getId(), savedGarage.getId());
        mockMvc.perform(post("http://localhost:8000/api/v1/parkings")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestModel2)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The car is already added to the garage"));
    }

    @Test
    public void shouldThrowExceptionGarageNotAcceptCarsWithGas() throws Exception {

        Car modelCar = new Car("Test", "Test", "Test", Fuel.PETROL, true);

        Car savedCar = objectMapper.readValue(mockMvc.perform(post("http://localhost:8000/api/v1/cars")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelCar)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carBrand").value(modelCar.getCarBrand()))
                .andExpect(jsonPath("$.model").value(modelCar.getModel()))
                .andExpect(jsonPath("$.power").value(modelCar.getPower()))
                .andExpect(jsonPath("$.fuel").value(modelCar.getFuel().toString()))
                .andReturn().getResponse().getContentAsByteArray(), Car.class);

        GarageDto modelGarage = new GarageDto("Test Ulica Test Miasto", "100", false);

        Garage savedGarage = objectMapper.readValue(mockMvc.perform(post("http://localhost:8000/api/v1/garages")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelGarage)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value(modelGarage.getAddress()))
                .andExpect(jsonPath("$.numberOfParkingSpaces").value(modelGarage.getNumberOfParkingSpaces()))
                .andExpect(jsonPath("$.lpgAllowed").value(modelGarage.isLpgAllowed()))
                .andReturn().getResponse().getContentAsByteArray(), Garage.class);

        RequestModel requestModel = new RequestModel(savedCar.getId(), savedGarage.getId());

        mockMvc.perform(post("http://localhost:8000/api/v1/parkings")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestModel)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The garage does not allow cars with gas"));
    }
}
