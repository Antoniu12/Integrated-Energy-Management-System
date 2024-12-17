package ro.tuc.ds2020.dtos;


import ro.tuc.ds2020.dtos.validators.annotation.AgeLimit;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class DeviceDetailsDTO {

    private UUID id;
    private UUID userId;
    @NotNull
    private String description;
    @NotNull
    private String address;
    @NotNull
    private int energyConsumption;

    public DeviceDetailsDTO() {
    }

    public DeviceDetailsDTO(UUID userId, String description, String address, int energyConsumption) {
        this.userId = userId;
        this.description = description;
        this.address = address;
        this.energyConsumption = energyConsumption;
    }

    public DeviceDetailsDTO(UUID id,UUID userId, String description, String address, int energyConsumption) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.address = address;
        this.energyConsumption = energyConsumption;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(int energyConsumption) {
        this.energyConsumption = energyConsumption;
    }
}
