package org.softuni.dtos.suppliers;

public class EditSupplierDto {

    private Long id;

    private String name;

    private boolean importer;

    public EditSupplierDto() {
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

    public boolean isImporter() {
        return this.importer;
    }

    public void setImporter(boolean importer) {
        this.importer = importer;
    }
}
