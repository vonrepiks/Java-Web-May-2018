package models;

public class Product {

    private String name;

    private String description;

    private ProductType productType;

    public Product() {
    }

    public Product(String name, String description, ProductType productType) {
        this.name = name;
        this.description = description;
        this.productType = productType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductType getProductType() {
        return this.productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
