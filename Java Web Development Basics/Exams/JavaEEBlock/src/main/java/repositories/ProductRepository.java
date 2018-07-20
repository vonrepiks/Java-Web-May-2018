package repositories;

import models.Product;
import models.ProductType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductRepository {

    private List<Product> products;

    public ProductRepository() {
        this.products = new ArrayList<>();
        this.seedProducts();
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(this.products);
    }

    private void seedProducts() {
        this.products.add(new Product("Chushkopek", "A universal tool for …", ProductType.valueOf("Domestic".toUpperCase())));
        this.products.add(new Product("Injektoplqktor", "Dunno what this is…", ProductType.valueOf("Cosmetic".toUpperCase())));
        this.products.add(new Product("Plumbus", "A domestic tool for everything", ProductType.valueOf("Food".toUpperCase())));
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }
}
