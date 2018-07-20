package entities;

import entities.converters.ProductTypeConverter;
import org.hibernate.annotations.GenericGenerator;
import utility.DecimalFormatter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {
    private String id;

    private String name;

    private Double price;

    private String description;

    private ProductType type;

    private Set<Order> orders;

    public Product() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    @Convert(converter=ProductTypeConverter.class)
    public ProductType getType() {
        return this.type;
    }

    @Column(nullable = false)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String extractHomeView() {
        return (new StringBuilder()
            .append("<a href=\"/products/details/" + this.getId() + "\" class=\"col-md-2\">")
                .append("<div class=\"product p-1 chushka-bg-color rounded-top rounded-bottom\">")
                .append("<h5 class=\"text-center mt-3\">" + this.getName() + "</h5>")
                .append("<hr class=\"hr-1 bg-white\"/>")
                .append("<p class=\"text-white text-center description\">")
                .append(this.getDescription().length() > 50 ? this.getDescription().substring(0, 50 + 1) + "..." : this.getDescription())
                .append("</p>")
                .append("<hr class=\"hr-1 bg-white\"/>")
                .append("<h6 class=\"text-center text-white mb-3\">$" + DecimalFormatter.getDecimalFormat().format(this.getPrice()) + "</h6>")
                .append("</div>")
                .append("</a>")
        ).toString();
    }
}
