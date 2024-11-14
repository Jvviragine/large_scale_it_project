package lsit.Controllers;


import java.util.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lsit.Models.MenuItem;
import lsit.Repositories.MenuItemRepository;

@RestController
public class MenuController {
    MenuItemRepository menu;

    public MenuController(MenuItemRepository menu){
        this.menu = menu;
    }

    @GetMapping("/menu")
    public List<MenuItem> list(){
        return menu.list();
    }

    @GetMapping("/getMenuItem/{id}")
    public MenuItem get(@PathVariable("id") UUID id){
        return menu.get(id);
    }

    @PostMapping("/addMenuItem")
    public MenuItem add(@RequestBody MenuItem i){
        menu.add(i);
        return i;
    }

    @PutMapping("/updateMenuItem/{id}")
    public MenuItem update(@PathVariable("id") UUID id, @RequestBody MenuItem i){
        i.id = id;
        menu.update(i);
        return i;
    }

    @DeleteMapping("/deleteMenuItem/{id}")
    public void delete(@PathVariable("id") UUID id){
        menu.remove(id);
    }
    
}
