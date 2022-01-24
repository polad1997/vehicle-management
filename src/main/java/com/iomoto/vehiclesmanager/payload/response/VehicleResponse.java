package com.iomoto.vehiclesmanager.payload.response;

import com.iomoto.vehiclesmanager.model.Properties;
import com.iomoto.vehiclesmanager.payload.request.PropertiesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {
    private String id;
    private String name;
    private String vin;
    private String licensePlateNumber;
    private PropertiesDto properties;
}
