package com.iomoto.vehiclesmanager.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertiesDto {
    private String id;
    private String color;
    private Integer numberOfDoors;
    private Integer numberOfWheels;
}
