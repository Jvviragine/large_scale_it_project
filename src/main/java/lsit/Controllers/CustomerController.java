package lsit.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lsit.Models.Customer;
import lsit.Repositories.CustomerRepository;

/**
 * Controller for handling Customer-related HTTP requests.
 * 
 * Note: All order-related actions are managed through the OrderController.
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {
    
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Retrieves all customers.
     * 
     * @return List of all customers.
     */
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customers = customerRepository.list();
        return ResponseEntity.ok(customers);
    }

    /**
     * Retrieves a customer by their UUID.
     * 
     * @param id The UUID of the customer.
     * @return The Customer object if found, else 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable UUID id){
        Customer customer = customerRepository.get(id);
        if(customer != null){
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Adds a new customer.
     * 
     * @param customer The customer details.
     * @return The added Customer object.
     */
    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        customerRepository.add(customer);
        return ResponseEntity.ok(customer);
    }

    /**
     * Updates an existing customer.
     * 
     * @param id The UUID of the customer to update.
     * @param customerDetails The updated customer details.
     * @return The updated Customer object if found, else 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable UUID id, @RequestBody Customer customerDetails){
        customerDetails.setId(id);
        customerRepository.update(customerDetails);
        Customer updatedCustomer = customerRepository.get(id);
        if(updatedCustomer != null){
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a customer by their UUID.
     * 
     * @param id The UUID of the customer to delete.
     * @return 200 OK if deleted, else 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id){
        Customer customer = customerRepository.get(id);
        if(customer != null){
            customerRepository.remove(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
