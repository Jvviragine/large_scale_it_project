package lsit.Services;

import lsit.Exceptions.InvalidInputException;
import lsit.Exceptions.ResourceNotFoundException;
import lsit.Models.Order;
import lsit.Repositories.OrderRepository;
import lsit.Repositories.CustomerRepository;
import lsit.Repositories.MenuItemRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;

/**
 * Service class for managing orders.
 */
@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    /**
     * Retrieves all orders.
     * 
     * @return List of all orders.
     */
    public List<Order> getAllOrders() {
        logger.info("Fetching all orders");
        return orderRepository.list();
    }

    /**
     * Retrieves an order by its ID.
     * 
     * @param id The UUID of the order.
     * @return The Order object if found.
     * @throws ResourceNotFoundException if the order is not found.
     */
    public Order getOrderById(UUID id) {
        logger.info("Fetching order with ID: {}", id);
        Order order = orderRepository.get(id);
        if (order == null) {
            logger.error("Order not found with ID: {}", id);
            throw new ResourceNotFoundException("Could not find Order with provided ID");
        }
        return order;
    }

    /**
     * Adds a new order.
     * 
     * @param customerId The UUID of the customer.
     * @param menuItemIds The list of menu item UUIDs.
     * @return The added Order object.
     * @throws InvalidInputException if the customer ID or menu item IDs are invalid.
     */
    public Order addOrder(UUID customerId, List<UUID> menuItemIds) {
        logger.info("Adding order for customer: {}", customerId);
        var customer = customerRepository.get(customerId);
        if (customer == null) {
            logger.error("Invalid Customer ID: {}", customerId);
            throw new InvalidInputException("Invalid Customer ID");
        }

        var menuItems = menuItemIds.stream()
                .map(menuItemRepository::get)
                .filter(item -> item != null)
                .toList();

        if (menuItems.isEmpty() || menuItems.size() != menuItemIds.size()) {
            logger.error("Order must contain valid menu items");
            throw new InvalidInputException("Order must contain valid menu items");
        }

        Order order = new Order(customerId, menuItemIds);
        orderRepository.add(order);
        return order;
    }

    /**
     * Updates an existing order.
     * 
     * @param id The UUID of the order to update.
     * @param customerId The UUID of the customer.
     * @param menuItemIds The list of menu item UUIDs.
     * @return The updated Order object.
     * @throws ResourceNotFoundException if the order or customer is not found.
     * @throws InvalidInputException if the menu item IDs are invalid.
     */
    public Order updateOrder(UUID id, UUID customerId, List<UUID> menuItemIds) {
        logger.info("Updating order with ID: {}", id);
        Order order = orderRepository.get(id);
        if (order == null) {
            logger.error("Order not found with ID: {}", id);
            throw new ResourceNotFoundException("Order not found");
        }

        var customer = customerRepository.get(customerId);
        if (customer == null) {
            logger.error("Invalid Customer ID: {}", customerId);
            throw new InvalidInputException("Invalid Customer ID");
        }

        var menuItems = menuItemIds.stream()
                .map(menuItemRepository::get)
                .filter(item -> item != null)
                .toList();

        // The menu items should exist in the repository and the number of provided menu items 
        // should be equal to the number of menu items we are able to retrieve
        if (menuItems.isEmpty() || menuItems.size() != menuItemIds.size()) {
            logger.error("Order must contain valid menu items");
            throw new InvalidInputException("Order must contain valid menu items");
        }

        order.setCustomerId(customerId);
        order.setOrderItemIds(menuItemIds);
        orderRepository.update(order);
        return order;
    }

    /**
     * Deletes an order by its ID.
     * 
     * @param id The UUID of the order to delete.
     * @throws ResourceNotFoundException if the order is not found.
     */
    public void deleteOrder(UUID id) {
        logger.info("Deleting order with ID: {}", id);
        Order order = orderRepository.get(id);
        if (order == null) {
            logger.error("Order not found with ID: {}", id);
            throw new ResourceNotFoundException("Order not found");
        }
        orderRepository.remove(id);
    }
}
