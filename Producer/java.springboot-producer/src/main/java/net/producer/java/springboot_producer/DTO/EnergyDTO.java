package net.producer.java.springboot_producer.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class EnergyDTO {
    private int timestamp;
    private UUID device_id;
    private Float measurement_value;

}
