package lab.horo.grocery.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class ItemList {

    @Id
    @SequenceGenerator(
            name = "itemlist_id_sequence",
            sequenceName = "itemlist_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "itemlist_id_sequence"
    )
    @Column(name = "itemlist_id")
    private Long itemlist_id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_item_id")
    @ToString.Exclude
    private List<Item> items = new ArrayList<>();

    public ItemList() {
    }

    public ItemList(List<Item> items) {
        this.items = items;
    }

    public ItemList(String name) {
        this.name = name;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void removeItem(Item item){
        items.remove(item);
    }

    public void clearList(){
        items.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ItemList itemList = (ItemList) o;
        return itemlist_id != null && Objects.equals(itemlist_id, itemList.itemlist_id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
