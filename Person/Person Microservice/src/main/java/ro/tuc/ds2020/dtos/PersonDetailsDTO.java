package ro.tuc.ds2020.dtos;

import ro.tuc.ds2020.dtos.validators.annotation.AgeLimit;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class PersonDetailsDTO {

    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    private String address;
    @NotNull
    private int age;
    @NotNull
    private String role;

    public PersonDetailsDTO() {
    }

    public PersonDetailsDTO( String name, String password, String address, int age, String role) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.age = age;
        this.role = role;
    }

    public PersonDetailsDTO(UUID id, String name, String password, String address, int age, String role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.address = address;
        this.age = age;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword(){return password;}

    public void setPassword(String password){this.password=password;}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole(){return this.role;}

    public void setRole(String role){this.role = role;}
}
