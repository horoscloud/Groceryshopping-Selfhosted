package lab.horo.grocery.Controller;

import lab.horo.grocery.Entity.Item;
import lab.horo.grocery.Entity.ItemList;
import lab.horo.grocery.ItemService.ItemListService;
import lab.horo.grocery.ItemService.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/grocery-list")
public class ItemListController {

    private final ItemListService itemListService;
    private final ItemService itemService;

    @Autowired
    public ItemListController(ItemListService itemListService, ItemService itemService) {
        this.itemListService = itemListService;
        this.itemService = itemService;
    }


    @GetMapping()
    public ModelAndView listView(){
        ModelAndView itemlist = new ModelAndView("itemlist");
        itemlist.addObject("availableItems", itemService.findAllItems());

        List<ItemList> checkList = itemListService.findAllItemLists();
        boolean hasNonEmptyList = checkList.stream().anyMatch(list -> !list.getItems().isEmpty());
        itemlist.addObject("hasNonEmptyList", hasNonEmptyList);

        itemlist.addObject("allItemLists", itemListService.findAllItemLists());

        return itemlist;
    }

    @GetMapping("view")
    public ModelAndView groceryView(@RequestParam("listname") String name){
        ModelAndView grocerylist = new ModelAndView("grocerylist");
        grocerylist.addObject("availableItems", itemService.findAllItems());
        grocerylist.addObject("itemList", itemListService.findItemListByName(name));
        grocerylist.addObject("totalEstimatedPrice", itemListService.totalCostOfItems(name));

        return grocerylist;
    }

    @PostMapping("/create-list")
    public String createGroceryList(@RequestParam("listname") String name){
        itemListService.createItemList(name);
        return "redirect:/grocery-list";
    }


    @PostMapping("/add-item")
    public String addToGroceryList(@RequestParam("listname") String name,
                                   @RequestParam("item") Item item,
                                    @RequestParam("qty") Integer qty){

        itemListService.addToItemList(name, item, qty);
        return "redirect:/grocery-list/view?listname=" + name;
    }

    @PostMapping("/remove-item")
    public String removeFromGroceryList(@RequestParam("listname") String name,
                                   @RequestParam("item") String itemname){

        Item currentItem = itemService.findItemByName(itemname);
        if (currentItem != null) {
            itemListService.removeFromItemList(name, currentItem);
        }

        return "redirect:/grocery-list/view?listname=" + name;
    }

    @PostMapping("/clear-list")
    public String clearGroceryList(@RequestParam("listname") String name){
        itemListService.clearList(name);
        return "redirect:/grocery-list";
    }

    @PostMapping("/delete-list")
    public String deleteGroceryList(@RequestParam("listname") String name){
        itemListService.deleteList(name);
        return "redirect:/grocery-list";
    }

}
