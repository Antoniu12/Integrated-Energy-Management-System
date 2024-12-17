package ro.tuc.ds2020.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class Device implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name="id", columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "userid", columnDefinition = "BINARY(16)")
    private UUID user_id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "energy_consumption", nullable = false)
    private int energy_consumption;


    public Device() {
    }

    public Device(UUID user_id, String description, String address, int energy_consumption) {
        this.user_id = user_id;
        this.description = description;
        this.address = address;
        this.energy_consumption = energy_consumption;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getUserId(){return user_id;}
    public void setUserId(UUID user_id){
        this.user_id = user_id;
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
        return energy_consumption;
    }

    public void setEnergyConsumption(int energy_consumption) {
        this.energy_consumption = energy_consumption;
    }
}
