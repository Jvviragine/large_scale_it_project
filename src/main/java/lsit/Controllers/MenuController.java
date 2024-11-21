package lsit.Controllers;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lsit.Exceptions.InvalidInputException;
import lsit.Exceptions.ResourceNotFoundException;
import lsit.Models.MenuItem;
import lsit.Services.MenuItemService;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuItemService menuItemService;

    // access: customer, server, manager
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        List<MenuItem> menuItems = menuItemService.getAllMenuItems();
        return ResponseEntity.ok(menuItems);
    }

    // access: customer, server, manager
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable UUID id) {
        MenuItem menuItem = menuItemService.getMenuItemById(id);
        return ResponseEntity.ok(menuItem);
    }

    // access: manager
    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {
        MenuItem newMenuItem = menuItemService.addMenuItem(menuItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMenuItem);
    }

    // access: manager
    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable UUID id, @RequestBody MenuItem menuItemDetails) {
        MenuItem updatedMenuItem = menuItemService.updateMenuItem(id, menuItemDetails);
        return ResponseEntity.ok(updatedMenuItem);
    }

    // access: manager
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable UUID id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
