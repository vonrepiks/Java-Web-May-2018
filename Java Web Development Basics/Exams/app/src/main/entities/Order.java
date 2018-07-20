package entities;

import org.hibernate.annotations.GenericGenerator;
import utility.CustomDateTimeFormatter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    private String id;

    private Product product;

    private User client;

    private LocalDateTime orderedOn;

    public Order() {
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

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id", nullable = false)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "client_id", nullable = false)
    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    @Column(nullable = false)
    public LocalDateTime getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(LocalDateTime orderedOn) {
        this.orderedOn = orderedOn;
    }

    public String extractOrderAllView(int index) {
        index += 1;
        return (new StringBuilder()
                .append("<td class=\"col-md-1\">" + index + "</td>")
                .append("<td class=\"col-md-4\">" +  this.getId() + "</td>")
                .append("<td class=\"col-md-2\">" + this.client.getUsername() + "</td>")
                .append("<td class=\"col-md-2\">" + this.product.getName() + "</td>")
                .append("<td class=\"col-md-2\">" + this.orderedOn.format(CustomDateTimeFormatter.getDecimalFormat()) + "</td>")
        ).toString();
    }
}
