package lab.horo.grocery.ItemService;

import lab.horo.grocery.Entity.Item;
import lab.horo.grocery.Entity.ItemList;
import lab.horo.grocery.Repository.ItemListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemListService {

    private final ItemListRepository itemListRepository;

    @Autowired
    public ItemListService(ItemListRepository itemListRepository) {
        this.itemListRepository = itemListRepository;
    }

    public ItemList findItemListById(Long id){
        return itemListRepository.findById(id).get();
    }

    public ItemList findItemListByName(String name){
        if(itemListRepository.findItemListByName(name).isPresent())
            return itemListRepository.findItemListByName(name).get();
        else
            return null;
    }

    public List<ItemList> findAllItemLists(){
        return itemListRepository.findAll();
    }

    public void createItemList(String name){
        if(itemListRepository.findItemListByName(name).isEmpty())
            itemListRepository.save(new ItemList(name));
    }

    public void addToItemList(String name, Item item, Integer qty){
        if(itemListRepository.findItemListByName(name).isPresent()){
            ItemList list = itemListRepository.findItemListByName(name).get();
            item.setQty(qty);
            list.addItem(item);
            itemListRepository.save(list);
        }else{
            ItemList list = new ItemList(name);
            list.addItem(item);
            itemListRepository.save(list);
        }
    }

    public void removeFromItemList(String name, Item item){
        if(itemListRepository.findItemListByName(name).isPresent() && itemListRepository.findItemListByName(name).get().getItems().size() > 0){
            ItemList list = itemListRepository.findItemListByName(name).get();
            list.removeItem(item);
            itemListRepository.save(list);
        }
    }

    public void clearList(String name){
        if(itemListRepository.findItemListByName(name).isPresent()){
            ItemList list = itemListRepository.findItemListByName(name).get();
            list.clearList();
            itemListRepository.save(list);
        }
    }

    public void deleteList(String name){
        if(itemListRepository.findItemListByName(name).isPresent()){
            ItemList list = itemListRepository.findItemListByName(name).get();
            itemListRepository.delete(list);
        }
    }

    public Double totalCostOfItems(String listname){

        double totalEstimatedPrice = itemListRepository.findItemListByName(listname).get().getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQty())
                .sum();

        return totalEstimatedPrice;
    }
}
