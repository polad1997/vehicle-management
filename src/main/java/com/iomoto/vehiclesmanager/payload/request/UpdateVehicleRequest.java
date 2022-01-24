package com.iomoto.vehiclesmanager.payload.request;

import lombok.Data;

@Data
public class UpdateVehicleRequest {
    private String name;
    private String vin;
    private String licensePlateNumber;
    private PropertiesDto properties;
}
