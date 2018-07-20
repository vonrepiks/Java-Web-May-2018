package org.softuni.dtos.suppliers;

public class SupplierDto {

    private long id;

    private String name;

    private int countOfParts;

    public SupplierDto() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountOfParts() {
        return this.countOfParts;
    }

    public void setCountOfParts(int countOfParts) {
        this.countOfParts = countOfParts;
    }
}
