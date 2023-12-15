package lab.horo.grocery.ItemService;

import lab.horo.grocery.Entity.Item;
import lab.horo.grocery.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findItemByName(String name){
        if(itemRepository.findItemByName(name).isPresent()){
            return itemRepository.findItemByName(name).get();
        }else
            return null;
    }


    public List<Item> findAllItems(){
        return itemRepository.findAll();
    }


    public void createNewItem(String name, String brand, Double price){
        if(itemRepository.findItemByName(name).isEmpty()){
            Item item = new Item(name, brand, price);
            itemRepository.save(item);
        }else{
            Item item = itemRepository.findItemByName(name).get();
            item.setName(name);
            item.setBrand(brand);
            item.setPrice(price);
            itemRepository.save(item);
        }
    }

    public void removeItem(String name){
        if(itemRepository.findItemByName(name).isPresent()){
            itemRepository.delete(findItemByName(name));
        }
    }

    public void addQty(Item old, Integer qty){
        Item item = itemRepository.findById(old.getItem_id()).get();
        item.setQty(qty);
        itemRepository.save(item);
    }
}
