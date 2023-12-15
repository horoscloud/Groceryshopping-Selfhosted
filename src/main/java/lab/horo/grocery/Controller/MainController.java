package lab.horo.grocery.Controller;

import lab.horo.grocery.ItemService.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    private final ItemService itemService;

    @Autowired
    public MainController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping()
    public ModelAndView itemsView(){
        ModelAndView index = new ModelAndView("index");

        index.addObject("itemList", itemService.findAllItems());

        return index;
    }

    @PostMapping("/create-item")
    public String createItem(@RequestParam("name") String name,
                                     @RequestParam("price") Double price,
                                        @RequestParam("brand") String brand){

        itemService.createNewItem(name, brand, price);

        return "redirect:/";
    }

    @PostMapping("/remove-item")
    public String removeItem(@RequestParam("name") String name){

        itemService.removeItem(name);

        return "redirect:/";
    }

}
