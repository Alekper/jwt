package Application.models;

import lombok.*;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    @Getter
    @Column(nullable = false)
    private String category;

    @Getter
    @Column(nullable = false)
    private String name;

    @Getter
    @Column(nullable = false)
    private String description;

    @Getter
    @Column(nullable = false)
    private double price;

    @Getter
    @Column(nullable = false)
    private int inventory;

    @Getter
    @Column(nullable = false)
    private String paymentOptions;

    @Getter
    @Column(nullable = false)
    private String deliveryOptions;

    @Getter
    @Column(nullable = false)
    private int merchantId;

    public Product(String category, String name,
                   String description, double price, int inventory,
                   String paymentOptions, String deliveryOptions,int merchantId) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inventory = inventory;
        this.paymentOptions = paymentOptions;
        this.deliveryOptions = deliveryOptions;
        this.merchantId = merchantId;
    }

}
