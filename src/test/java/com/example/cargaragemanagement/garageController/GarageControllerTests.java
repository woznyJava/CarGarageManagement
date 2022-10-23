package com.example.cargaragemanagement.garageController;

import com.example.cargaragemanagement.garage.model.Garage;
import com.example.cargaragemanagement.garage.model.GarageDto;
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
public class GarageControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldSaveAndFindGarage() throws Exception {
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

        mockMvc.perform(get("http://localhost:8000/api/v1/garages/" + savedGarage.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value(savedGarage.getAddress()))
                .andExpect(jsonPath("$.numberOfParkingSpaces").value(savedGarage.getNumberOfParkingSpaces()))
                .andExpect(jsonPath("$.lpgAllowed").value(savedGarage.isLpgAllowed()));
    }

    @Test
    public void shouldUpdateGarage() throws Exception {

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

        GarageDto modelGarage2 = new GarageDto(savedGarage.getId(), "Update", "100 Update", false);
        Gson gson = new Gson();
        String json = gson.toJson(modelGarage2);

        mockMvc.perform(put("http://localhost:8000/api/v1/garages")
                        .contentType(APPLICATION_JSON)
                        .content(String.valueOf(json)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value(modelGarage2.getAddress()))
                .andExpect(jsonPath("$.numberOfParkingSpaces").value(modelGarage2.getNumberOfParkingSpaces()))
                .andExpect(jsonPath("$.lpgAllowed").value(modelGarage2.isLpgAllowed()));
    }
}