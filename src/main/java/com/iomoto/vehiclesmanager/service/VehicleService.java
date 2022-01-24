package com.iomoto.vehiclesmanager.service;

import com.iomoto.vehiclesmanager.model.Vehicle;
import com.iomoto.vehiclesmanager.payload.request.CreateVehicleRequest;
import com.iomoto.vehiclesmanager.payload.request.UpdateVehicleRequest;
import com.iomoto.vehiclesmanager.payload.response.VehicleResponse;

import java.util.List;

public interface VehicleService {

    VehicleResponse createVehicle(CreateVehicleRequest request);

    VehicleResponse updateVehicle(String id, UpdateVehicleRequest request);

    List<VehicleResponse> getAllVehicles();

    VehicleResponse getVehicleById(String id);

    void deleteVehicle(String id);

}
