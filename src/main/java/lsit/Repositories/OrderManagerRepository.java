package lsit.Repositories;

import org.springframework.stereotype.Repository;
import lsit.Models.OrderManager;
import java.util.*;

@Repository
public class OrderManagerRepository {
    static HashMap<UUID, OrderManager> orderManagers = new HashMap<>();

    // Add a new OrderManager
    public void add(OrderManager manager){
        manager.id = UUID.randomUUID();  // Generate a new UUID for the manager
        orderManagers.put(manager.id, manager);
    }

    // Retrieve an OrderManager by ID
    public OrderManager get(UUID id){
        return orderManagers.get(id);
    }

    // Remove an OrderManager by ID
    public void remove(UUID id){
        orderManagers.remove(id);
    }

    // Update an existing OrderManager
    public void update(OrderManager manager){
        OrderManager existingManager = orderManagers.get(manager.id);
        existingManager.name = manager.name;
        existingManager.managedOrders = manager.managedOrders;
    }

    // List all OrderManagers
    public List<OrderManager> list(){
        return new ArrayList<>(orderManagers.values());
    }
}
