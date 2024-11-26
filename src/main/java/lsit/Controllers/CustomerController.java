package lsit.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lsit.Exceptions.ResourceNotFoundException;
import lsit.Models.Customer;
import lsit.Services.CustomerService;

/**
 * Controller for handling Customer-related HTTP requests.
 * 
 * Note: All order-related actions are managed through the OrderController.
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;

    /**
     * Retrieves all customers. access: manager
     * 
     * @return List of all customers.
     */
    @GetMapping
    @PreAuthorize("hasRole('PIZZARIA_MANAGER')")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    /**
     * Retrieves a customer by their UUID. access: manager
     * 
     * @param id The UUID of the customer.
     * @return The Customer object if found, else 404 Not Found.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PIZZARIA_MANAGER')")
    public ResponseEntity<Customer> getCustomerById(@PathVariable UUID id){
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    /**
     * Adds a new customer. access: customer, server, manager
     * 
     * @param customer The customer details.
     * @return The added Customer object.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('PIZZARIA_SERVER', 'PIZZARIA_MANAGER', 'PIZZARIA_CUSTOMER')")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        Customer newCustomer = customerService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }

    /**
     * Updates an existing customer. access: server, manager
     * 
     * @param id The UUID of the customer to update.
     * @param customerDetails The updated customer details.
     * @return The updated Customer object if found, else 404 Not Found.
     */
    @PutMapping
    @PreAuthorize("hasAnyRole('PIZZARIA_SERVER', 'PIZZARIA_MANAGER')")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customerDetails){
        Customer updatedCustomer = customerService.updateCustomer(customerDetails);
        return ResponseEntity.ok(updatedCustomer);
    }

    /**
     * Deletes a customer by their UUID. access: server, manager
     * 
     * @param id The UUID of the customer to delete.
     * @return 200 OK if deleted, else 404 Not Found.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PIZZARIA_SERVER', 'PIZZARIA_MANAGER')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id){
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
