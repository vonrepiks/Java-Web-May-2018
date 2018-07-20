package org.softuni.dtos.parts;

public class PartCreateCarDto {

    private Long id;

    private String name;

    public PartCreateCarDto() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
