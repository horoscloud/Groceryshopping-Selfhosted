package lab.horo.grocery.Entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Item {
    @Id
    @SequenceGenerator(
            name = "item_id_sequence",
            sequenceName = "item_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "item_id_sequence"
    )
    @Column(name = "item_id")
    private Long item_id;

    private String name;
    private String brand;
    private Integer qty;
    private Double price; //Price for 1 Item

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemlist_id")
    private ItemList itemList;

    public Item() {
    }

    public Item(String name, String brand, Double price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public Item(String name, String brand, Integer qty, Double price) {
        this.name = name;
        this.brand = brand;
        this.qty = qty;
        this.price = price;
    }

    @Override
    public String toString() {
        double summaryPrice = qty * price;
        return "{" +
                "\"name\":\"" + name + "\"," +
                "\"qty\":" + qty + "," +
                "\"price\":" + price + "," +
                "\"summaryPrice\":" + summaryPrice +
                "}";
    }
}
