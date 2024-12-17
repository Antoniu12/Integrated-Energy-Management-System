package ro.tuc.ds2020.dtos;

import java.util.Objects;
import java.util.UUID;

public class DeviceDTO {
    private UUID id;
    private UUID userId;
    private String description;
    private String address;
    private int energyConsumption;

    public DeviceDTO() {
    }

    public DeviceDTO(UUID id, UUID userId, String description, String address, int energyConsumption) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO deviceDTO = (DeviceDTO) o;
        return energyConsumption == deviceDTO.energyConsumption &&
                Objects.equals(description, deviceDTO.description) &&
                address == deviceDTO.address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, energyConsumption);
    }
}
