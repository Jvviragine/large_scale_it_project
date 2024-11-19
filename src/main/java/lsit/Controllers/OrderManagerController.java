package lsit.Controllers;

import org.springframework.web.bind.annotation.*;
import lsit.Models.OrderManager;
import lsit.Repositories.OrderManagerRepository;

import java.util.*;

@RestController
public class OrderManagerController {
    
    private final OrderManagerRepository orderManagerRepository;

    // Constructor to inject the OrderManagerRepository
    public OrderManagerController(OrderManagerRepository orderManagerRepository){
        this.orderManagerRepository = orderManagerRepository;
    }

    // List all OrderManagers
    @GetMapping("/orderManagers")
    public List<OrderManager> list() {
        return orderManagerRepository.list();
    }

    // Get a specific OrderManager by ID
    @GetMapping("/orderManagers/{id}")
    public OrderManager get(@PathVariable("id") UUID id) {
        return orderManagerRepository.get(id);
    }

    // Add a new OrderManager
    @PostMapping("/orderManagers")
    public OrderManager add(@RequestBody OrderManager manager) {
        orderManagerRepository.add(manager);
        return manager;
    }

    // Update an existing OrderManager
    @PutMapping("/orderManagers/{id}")
    public OrderManager update(@PathVariable("id") UUID id, @RequestBody OrderManager manager) {
        manager.id = id;  // Set the ID to ensure the correct manager is updated
        orderManagerRepository.update(manager);
        return manager;
    }

    // Delete an OrderManager by ID
    @DeleteMapping("/orderManagers/{id}")
    public void delete(@PathVariable("id") UUID id) {
        orderManagerRepository.remove(id);
    }
}
