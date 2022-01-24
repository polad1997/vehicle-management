package com.iomoto.vehiclesmanager.service;

import com.iomoto.vehiclesmanager.exception.ResourceNotFoundException;
import com.iomoto.vehiclesmanager.mapper.DocumentDtoMapper;
import com.iomoto.vehiclesmanager.model.Properties;
import com.iomoto.vehiclesmanager.model.Vehicle;
import com.iomoto.vehiclesmanager.payload.request.CreateVehicleRequest;
import com.iomoto.vehiclesmanager.payload.request.PropertiesDto;
import com.iomoto.vehiclesmanager.payload.request.UpdateVehicleRequest;
import com.iomoto.vehiclesmanager.payload.response.VehicleResponse;
import com.iomoto.vehiclesmanager.repository.VehicleRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Spy
    private DocumentDtoMapper documentDtoMapper;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule().silent();

    @Test
    public void shouldReturnAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle());

        List<VehicleResponse> responseList;

        given(vehicleRepository.findAll()).willReturn(vehicles);

        responseList = vehicleService.getAllVehicles();

        assertThat(responseList.equals(vehicles));

        verify(vehicleRepository).findAll();
    }

    @Test
    public void whenGivenIdShouldReturnVehicleIfFound() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("61ec89db7513fa6a8a81fe5a");
        vehicle.setName("Test name");

        VehicleResponse response;

        when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));

        response = vehicleService.getVehicleById(vehicle.getId());

        assertThat(response.equals(vehicle));
        verify(vehicleRepository).findById(vehicle.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowExceptionWhenVehicleDoesntExistWhileGetting() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("61ec89db7513fa6a8a81fe5a");
        vehicle.setName("Test Name");

        given(vehicleRepository.findById(anyString())).willReturn(Optional.ofNullable(null));
        vehicleService.getVehicleById(vehicle.getId());
    }

    @Test
    public void whenSaveVehicleShouldReturnVehicle() {
        Vehicle vehicle = new Vehicle();
        Properties properties = new Properties();
        vehicle.setProperties(properties);

        CreateVehicleRequest request = new CreateVehicleRequest();
        PropertiesDto propertiesDto = new PropertiesDto();
        request.setProperties(propertiesDto);

        VehicleResponse response = documentDtoMapper.mapVehicleDocumentToDto(vehicle);
        response.setProperties(documentDtoMapper.mapDocumentToDto(vehicle.getProperties()));

        when(vehicleRepository.save(ArgumentMatchers.any(Vehicle.class))).thenReturn(vehicle);

        response = vehicleService.createVehicle(request);

        assertThat(response.getName()).isSameAs(vehicle.getName());
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    public void whenGivenIdShouldUpdateVehicleIfFound() {
        Vehicle vehicle = new Vehicle();
        Properties properties = new Properties();
        vehicle.setProperties(properties);
        vehicle.setName("Test Name");
        vehicle.setId("61ed67020abf76387894707e");

        UpdateVehicleRequest request = new UpdateVehicleRequest();
        PropertiesDto propertiesDto = new PropertiesDto();
        request.setProperties(propertiesDto);
        request.setName("New Test Name");

        VehicleResponse response = documentDtoMapper.mapVehicleDocumentToDto(vehicle);
        response.setProperties(documentDtoMapper.mapDocumentToDto(vehicle.getProperties()));

        when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));

        response = vehicleService.updateVehicle(vehicle.getId(), request);

        assertEquals(response.getId(), vehicle.getId());
        assertEquals(response.getName(), vehicle.getName());
        verify(vehicleRepository).findById(response.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowExceptionWhenVehicleDoesntExistWhileUpdating() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("61ed67020abf76387894707e");
        vehicle.setName("Test Name");

        UpdateVehicleRequest request = new UpdateVehicleRequest();
        request.setName("New Test Name");
        PropertiesDto propertiesDto = new PropertiesDto();
        request.setProperties(propertiesDto);

        given(vehicleRepository.findById(anyString())).willReturn(Optional.ofNullable(null));
        vehicleService.updateVehicle(vehicle.getId(), request);
    }

    @Test
    public void whenGivenIdShouldDeleteUserIfFound() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Test Name");
        vehicle.setId("61ed67020abf76387894707e");

        when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));

        vehicleService.deleteVehicle(vehicle.getId());
        verify(vehicleRepository).delete(vehicle);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowExceptionWhenVehicleDoesntExistWhileDeleting() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Test Name");

        given(vehicleRepository.findById(anyString())).willReturn(Optional.ofNullable(null));
        vehicleService.deleteVehicle(vehicle.getId());
    }

}