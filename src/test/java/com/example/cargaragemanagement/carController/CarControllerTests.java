package com.example.cargaragemanagement.carController;

import com.example.cargaragemanagement.car.model.Car;
import com.example.cargaragemanagement.car.Fuel;
import com.example.cargaragemanagement.car.model.CarUpdateModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldSaveAndFindCar() throws Exception {

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

        mockMvc.perform(get("http://localhost:8000/api/v1/cars/" + savedCar.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carBrand").value(savedCar.getCarBrand()))
                .andExpect(jsonPath("$.model").value(savedCar.getModel()))
                .andExpect(jsonPath("$.fuel").value(savedCar.getFuel().toString()))
                .andExpect(jsonPath("$.parked").value(savedCar.isParked()));
    }

    @Test
    public void shouldUpdateCar() throws Exception {

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

        CarUpdateModel modelCar2 = new CarUpdateModel(savedCar.getId(), "Update", "Update", "Update", Fuel.DIESEL, false);
        Car updatedCar = objectMapper.readValue(mockMvc.perform(put("http://localhost:8000/api/v1/cars")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelCar2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carBrand").value(modelCar2.getCarBrand()))
                .andExpect(jsonPath("$.model").value(modelCar2.getModel()))
                .andExpect(jsonPath("$.power").value(modelCar2.getPower()))
                .andExpect(jsonPath("$.fuel").value(modelCar2.getFuel().toString()))
                .andReturn().getResponse().getContentAsByteArray(), Car.class);

        mockMvc.perform(get("http://localhost:8000/api/v1/cars/" + updatedCar.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carBrand").value(updatedCar.getCarBrand()))
                .andExpect(jsonPath("$.model").value(updatedCar.getModel()))
                .andExpect(jsonPath("$.fuel").value(updatedCar.getFuel().toString()))
                .andExpect(jsonPath("$.parked").value(updatedCar.isParked()));
    }

    @Test
    public void shouldDeleteCar() throws Exception {

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

        mockMvc.perform(delete("http://localhost:8000/api/v1/cars/" + savedCar.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8000/api/v1/cars/" + savedCar.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carBrand").value(savedCar.getCarBrand()))
                .andExpect(jsonPath("$.model").value(savedCar.getModel()))
                .andExpect(jsonPath("$.deleted").value(true))
                .andExpect(jsonPath("$.fuel").value(savedCar.getFuel().toString()));
    }
}
