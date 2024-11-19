package lsit.Repositories;

import java.util.*;
import org.springframework.stereotype.Repository;
import lsit.Models.Customer;

/**
 * Repository for managing Customer data.
 * Utilizes in-memory storage with a HashMap.
 */
@Repository
public class CustomerRepository {
    
    // In-memory storage for customers
    private static HashMap<UUID, Customer> customers = new HashMap<>();

    /**
     * Adds a new customer.
     * Generates a unique UUID for the customer.
     * 
     * @param customer The customer to add.
     */
    public void add(Customer customer){
        customer.setId(UUID.randomUUID());
        customers.put(customer.getId(), customer);
    }

    /**
     * Retrieves a customer by ID.
     * 
     * @param id The UUID of the customer.
     * @return The Customer object or null if not found.
     */
    public Customer get(UUID id){
        return customers.get(id);
    }

    /**
     * Removes a customer by ID.
     * 
     * @param id The UUID of the customer to remove.
     */
    public void remove(UUID id){
        customers.remove(id);
    }

    /**
     * Updates an existing customer's details.
     * 
     * @param customer The customer with updated information.
     */
    public void update(Customer customer){
        Customer existingCustomer = customers.get(customer.getId());
        if(existingCustomer != null){
            existingCustomer.setName(customer.getName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        }
    }

    /**
     * Lists all customers.
     * 
     * @return A list of all customers.
     */
    public List<Customer> list(){
        return new ArrayList<>(customers.values());
    }
}
