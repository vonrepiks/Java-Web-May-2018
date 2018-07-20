package org.softuni.dtos.suppliers;

public class CreateSupplierDto {

    private String name;

    private boolean importer;

    public CreateSupplierDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return this.importer;
    }

    public void setImporter(boolean importer) {
        this.importer = importer;
    }
}
