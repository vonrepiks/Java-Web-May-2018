package org.softuni.dtos.suppliers;

public class SupplierDtoForCreatingPart {

    private Long id;

    private String name;

    public SupplierDtoForCreatingPart() {
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
