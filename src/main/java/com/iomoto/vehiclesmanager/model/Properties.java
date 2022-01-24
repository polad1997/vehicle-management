package com.iomoto.vehiclesmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Properties {
    @Id
    private String id;
    private String color;
    private Integer numberOfDoors;
    private Integer numberOfWheels;

    public Properties(String color, Integer numberOfDoors, Integer numberOfWheels) {
        this.color = color;
        this.numberOfDoors = numberOfDoors;
        this.numberOfWheels = numberOfWheels;
    }
}
