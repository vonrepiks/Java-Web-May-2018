package entities.converters;

import entities.ProductType;

import javax.persistence.AttributeConverter;

public class ProductTypeConverter implements AttributeConverter<ProductType, String> {

    @Override
    public String convertToDatabaseColumn(ProductType productType) {
        return productType.toString();
    }

    @Override
    public ProductType convertToEntityAttribute(String s) {
        return ProductType.valueOf(s.toUpperCase());
    }
}
