package com.iomoto.vehiclesmanager.service;

import com.iomoto.vehiclesmanager.exception.ResourceNotFoundException;
import com.iomoto.vehiclesmanager.mapper.DocumentDtoMapper;
import com.iomoto.vehiclesmanager.model.Vehicle;
import com.iomoto.vehiclesmanager.payload.request.CreateVehicleRequest;
import com.iomoto.vehiclesmanager.payload.request.UpdateVehicleRequest;
import com.iomoto.vehiclesmanager.payload.response.VehicleResponse;
import com.iomoto.vehiclesmanager.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private DocumentDtoMapper documentDtoMapper;

    @Override
    public VehicleResponse createVehicle(CreateVehicleRequest request) {
        Vehicle vehicle = new Vehicle();
        vehicle.setProperties(documentDtoMapper.mapDtoToDocument(request.getProperties()));
        vehicle.setName(request.getName());
        vehicle.setLicensePlateNumber(request.getLicensePlateNumber());
        vehicle.setVin(request.getVin());
        vehicleRepository.save(vehicle);
        return documentDtoMapper.mapVehicleDocumentToDto(vehicle);
    }

    @Override
    public VehicleResponse updateVehicle(String id, UpdateVehicleRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id : " + id));
        vehicle.setId(id);
        vehicle.setName(request.getName());
        vehicle.setVin(request.getVin());
        vehicle.setLicensePlateNumber(request.getLicensePlateNumber());
        vehicle.setProperties(documentDtoMapper.mapDtoToDocument(request.getProperties()));
        vehicleRepository.save(vehicle);
        return documentDtoMapper.mapVehicleDocumentToDto(vehicle);
    }

    @Override
    public List<VehicleResponse> getAllVehicles() {
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        return vehicleList.stream()
                .map(d -> documentDtoMapper
                        .mapVehicleDocumentToDto(d)).collect(Collectors.toList());
    }

    @Override
    public VehicleResponse getVehicleById(String id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id : " + id));
        VehicleResponse response = documentDtoMapper.mapVehicleDocumentToDto(vehicle);
        return response;
    }

    @Override
    public void deleteVehicle(String id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id : " + id));
        vehicleRepository.delete(vehicle);
    }

}
