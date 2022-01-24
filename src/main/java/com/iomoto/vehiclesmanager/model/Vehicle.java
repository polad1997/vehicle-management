package com.iomoto.vehiclesmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    private String id;
    private String name;
    private String vin;
    private String licensePlateNumber;
    private Properties properties;
}
