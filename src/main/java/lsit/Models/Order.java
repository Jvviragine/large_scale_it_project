package lsit.Models;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Order {
    private UUID id;

    @NotNull(message = "Customer ID cannot be null")
    private UUID customerId; 

    @NotNull(message = "Order items cannot be null")
    @Size(min = 1, message = "Order must contain at least one item")
    private List<UUID> orderItemIds;


    // Default constructor
    public Order() {
        this.id = UUID.randomUUID();
    }

    // Parameterized constructor
    public Order(UUID customerId, List<UUID> orderItemIds) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.orderItemIds = orderItemIds;
    }

    // Getter for the 'id'
    public UUID getId() {
        return id;
    }
    // Setter for the 'id'
    public void setId(UUID id) {
        this.id = id;
    }
    // Getter for 'customerId'
    public UUID getCustomerId() {
        return customerId;
    }
    // Setter for 'customerId'
    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }
    // Getter for 'orderItemIds'
    public List<UUID> getOrderItemIds() {
        return orderItemIds;
    }
    // Setter for 'orderItemIds'
    public void setOrderItemIds(List<UUID> orderItemIds) {
        this.orderItemIds = orderItemIds;
    }
    
}

