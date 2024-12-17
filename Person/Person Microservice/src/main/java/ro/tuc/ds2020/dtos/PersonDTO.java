package ro.tuc.ds2020.dtos;

import java.util.Objects;
import java.util.UUID;

public class PersonDTO {
    private UUID id;
    private String name;
    private String password;
    private String address;
    private int age;
    private String role;

    public PersonDTO() {
    }

    public PersonDTO(UUID id, String name, String password, String address, int age, String role) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword(){return password;}

    public void setPassword(String password){this.password=password;}

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole(){return this.role;}

    public void setRole(String role){this.role = role;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return age == personDTO.age &&
                Objects.equals(name, personDTO.name) &&
                role == personDTO.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
