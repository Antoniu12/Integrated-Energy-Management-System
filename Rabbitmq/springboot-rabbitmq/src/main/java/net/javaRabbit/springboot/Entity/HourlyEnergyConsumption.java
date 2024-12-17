package net.javaRabbit.springboot.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "HourlyEnergyConsumption")
public class HourlyEnergyConsumption {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name="id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "device_id", columnDefinition = "BINARY(16)")
    private UUID deviceId;

    @Column(name = "timestamp", nullable = false)
    private int timestamp;

    @Column(name = "hourly_consumption", nullable = false)
    private Float hourlyConsumption;

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public Float getHourlyConsumption() {
        return hourlyConsumption;
    }

    public void setHourlyConsumption(Float hourlyConsumption) {
        this.hourlyConsumption = hourlyConsumption;
    }
}
