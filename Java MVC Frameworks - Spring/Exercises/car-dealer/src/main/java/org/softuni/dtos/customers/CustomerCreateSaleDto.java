package org.softuni.dtos.customers;

public class CustomerCreateSaleDto {

    private Long id;

    private String name;

    private boolean young;

    public CustomerCreateSaleDto() {
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

    public boolean isYoung() {
        return this.young;
    }

    public void setYoung(boolean young) {
        this.young = young;
    }
}
