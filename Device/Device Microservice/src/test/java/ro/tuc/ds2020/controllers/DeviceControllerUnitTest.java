package ro.tuc.ds2020.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ro.tuc.ds2020.Ds2020TestConfig;
import ro.tuc.ds2020.dtos.DeviceDetailsDTO;
import ro.tuc.ds2020.services.DeviceService;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class DeviceControllerUnitTest extends Ds2020TestConfig {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService service;

    @Test
    public void insertPersonTest() throws Exception {
        UUID userId = UUID.randomUUID();
        ObjectMapper objectMapper = new ObjectMapper();
        DeviceDetailsDTO personDTO = new DeviceDetailsDTO(userId,"Microcontroller", "Somewhere Else street", 22);

        mockMvc.perform(post("/device/insert")
                .content(objectMapper.writeValueAsString(personDTO))
                .contentType("application/json"))
                .andExpect(status().isCreated());
    }

    @Test
    public void insertPersonTestFailsDueToAge() throws Exception {
        UUID userId = UUID.randomUUID();
        ObjectMapper objectMapper = new ObjectMapper();
        DeviceDetailsDTO personDTO = new DeviceDetailsDTO(userId,"Microcontroller", "Somewhere Else street", 17);

        mockMvc.perform(post("/device/insert")
                .content(objectMapper.writeValueAsString(personDTO))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void insertPersonTestFailsDueToNull() throws Exception {
        UUID userId = UUID.randomUUID();
        ObjectMapper objectMapper = new ObjectMapper();
        DeviceDetailsDTO personDTO = new DeviceDetailsDTO(userId,"Microcontroller", null, 17);

        mockMvc.perform(post("/device/insert")
                .content(objectMapper.writeValueAsString(personDTO))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
}
