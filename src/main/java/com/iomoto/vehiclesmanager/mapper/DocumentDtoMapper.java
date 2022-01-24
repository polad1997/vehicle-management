package com.iomoto.vehiclesmanager.mapper;

import com.iomoto.vehiclesmanager.model.Properties;
import com.iomoto.vehiclesmanager.model.Vehicle;
import com.iomoto.vehiclesmanager.payload.request.PropertiesDto;
import com.iomoto.vehiclesmanager.payload.response.VehicleResponse;
import org.springframework.stereotype.Service;

@Service
public class DocumentDtoMapper {

    public Properties mapDtoToDocument(PropertiesDto propertiesDto) {
        if (propertiesDto != null) {
            Properties properties = new Properties();
            properties.setId(propertiesDto.getId());
            properties.setColor(propertiesDto.getColor());
            properties.setNumberOfDoors(propertiesDto.getNumberOfDoors());
            properties.setNumberOfWheels(propertiesDto.getNumberOfWheels());
            return properties;
        }
        return null;
    }

    public PropertiesDto mapDocumentToDto(Properties properties) {
        if (properties != null) {
            PropertiesDto propertiesDto = new PropertiesDto();
            propertiesDto.setId(properties.getId());
            propertiesDto.setColor(properties.getColor());
            propertiesDto.setNumberOfDoors(properties.getNumberOfDoors());
            propertiesDto.setNumberOfWheels(properties.getNumberOfWheels());
            return propertiesDto;
        }
        return null;
    }

    public VehicleResponse mapVehicleDocumentToDto(Vehicle vehicle) {
        if (vehicle != null) {
            VehicleResponse vehicleResponse = new VehicleResponse();
            vehicleResponse.setId(vehicle.getId());
            vehicleResponse.setName(vehicle.getName());
            vehicleResponse.setVin(vehicle.getVin());
            vehicleResponse.setLicensePlateNumber(vehicle.getLicensePlateNumber());
            PropertiesDto propertiesDto = mapDocumentToDto(vehicle.getProperties());
            vehicleResponse.setProperties(propertiesDto);
            return vehicleResponse;
        }

        return null;
    }
}
