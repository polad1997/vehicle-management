package com.iomoto.vehiclesmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iomoto.vehiclesmanager.exception.ResourceNotFoundException;
import com.iomoto.vehiclesmanager.mapper.DocumentDtoMapper;
import com.iomoto.vehiclesmanager.model.Vehicle;
import com.iomoto.vehiclesmanager.payload.request.CreateVehicleRequest;
import com.iomoto.vehiclesmanager.payload.request.PropertiesDto;
import com.iomoto.vehiclesmanager.payload.request.UpdateVehicleRequest;
import com.iomoto.vehiclesmanager.payload.response.VehicleResponse;
import com.iomoto.vehiclesmanager.service.VehicleServiceImpl;
import com.iomoto.vehiclesmanager.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleServiceImpl vehicleService;

    @Spy
    private DocumentDtoMapper documentDtoMapper;

    @Test
    public void listAllVehiclesWhenGetMethod() throws Exception {

        Vehicle vehicle = new Vehicle();
        vehicle.setName("Test name");
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle);

        List<VehicleResponse> responseList = vehicleList.stream()
                .map(d -> documentDtoMapper
                        .mapVehicleDocumentToDto(d)).collect(Collectors.toList());

        given(vehicleService.getAllVehicles()).willReturn(responseList);

        mockMvc.perform(get("/api/v1/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(vehicle.getName())));
    }

    @Test
    public void shouldReturnVehicleByIdWhenGetMethod() throws Exception {

        Vehicle vehicle = new Vehicle();
        vehicle.setName("Test Name");
        vehicle.setId("61ec89db7513fa6a8a81fe5a");

        VehicleResponse response = documentDtoMapper.mapVehicleDocumentToDto(vehicle);

        given(vehicleService.getVehicleById(vehicle.getId())).willReturn(response);

        mockMvc.perform(get("/api/v1/vehicles/" + vehicle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(vehicle.getName())));
    }

    @Test
    public void shouldThrowExceptionWhenVehicleDoesntExist() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("61ec89db7513fa6a8a81fe5a");
        vehicle.setName("Test Name");

        doThrow(new ResourceNotFoundException(vehicle.getId())).when(vehicleService).getVehicleById(vehicle.getId());

        mockMvc.perform(get("/api/v1/vehicles/" + vehicle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createVehiclewhenPostMethod() throws Exception {

        Vehicle vehicle = new Vehicle();
        vehicle.setName("Test Name");

        CreateVehicleRequest request = new CreateVehicleRequest();
        request.setName("Test Name");
        PropertiesDto propertiesDto = new PropertiesDto();
        request.setProperties(propertiesDto);

        VehicleResponse response = documentDtoMapper.mapVehicleDocumentToDto(vehicle);
        response.setName("Test Name");

        given(vehicleService.createVehicle(request)).willReturn(response);

        mockMvc.perform(post("/api/v1/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(vehicle.getName())));
    }

    @Test
    public void updateVehiclewhenPutMethod() throws Exception {

        Vehicle vehicle = new Vehicle();
        vehicle.setName("Test Name");
        vehicle.setId("61ec89db7513fa6a8a81fe5a");

        UpdateVehicleRequest request = new UpdateVehicleRequest();
        request.setName("Test Name");

        VehicleResponse response = documentDtoMapper.mapVehicleDocumentToDto(vehicle);

        given(vehicleService.updateVehicle(vehicle.getId(), request)).willReturn(response);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(put("/api/v1/vehicles/" + vehicle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(vehicle.getName())));
    }

    @Test
    public void shouldThrowExceptionWhenVehicleDoesntExistPut() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("61ec89db7513fa6a8a81fe5a");
        vehicle.setName("Test Name");

        UpdateVehicleRequest request = new UpdateVehicleRequest();
        request.setName("Test Name");

        VehicleResponse response = documentDtoMapper.mapVehicleDocumentToDto(vehicle);

        doThrow(new ResourceNotFoundException(vehicle.getId())).when(vehicleService).updateVehicle(vehicle.getId(), request);
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(put("/api/v1/vehicles/" + vehicle.getId())
                        .content(mapper.writeValueAsString(response))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeVehicleByIdWhenDeleteMethod() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Test Name");
        vehicle.setId("61ec89db7513fa6a8a81fe5a");

        doNothing().when(vehicleService).deleteVehicle(vehicle.getId());

        mockMvc.perform(delete("/api/v1/vehicles/" + vehicle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldThrowExceptionWhenVehicleDoesntExistDelete() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Test Name");
        vehicle.setId("61ec89db7513fa6a8a81fe5a");

        doThrow(new ResourceNotFoundException(vehicle.getId())).when(vehicleService).deleteVehicle(vehicle.getId());

        mockMvc.perform(delete("/users/" + vehicle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isNotFound());

    }
}