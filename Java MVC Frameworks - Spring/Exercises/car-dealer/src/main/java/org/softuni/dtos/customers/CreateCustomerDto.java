package org.softuni.dtos.customers;

public class CreateCustomerDto {

    private String name;

    private String birthDate;

    public CreateCustomerDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
