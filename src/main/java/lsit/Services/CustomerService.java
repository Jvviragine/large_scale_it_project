package lsit.Services;

import lsit.Exceptions.ResourceNotFoundException;
import lsit.Models.Customer;
import lsit.Repositories.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;

/**
 * Service class for managing customers.
 */
@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Retrieves all customers.
     *
     * @return List of all customers.
     */
    public List<Customer> getAllCustomers() {
        logger.info("Fetching all customers");
        return customerRepository.list();
    }

    /**
     * Retrieves a customer by its ID.
     *
     * @param id The UUID of the customer.
     * @return The Customer object if found.
     * @throws ResourceNotFoundException if the customer is not found.
     */
    public Customer getCustomerById(UUID id) {
        logger.info("Fetching customer with ID: {}", id);
        Customer customer = customerRepository.get(id);
        if (customer == null) {
            logger.error("Customer not found with ID: {}", id);
            throw new ResourceNotFoundException("Customer not found");
        }
        return customer;
    }

    /**
     * Adds a new customer.
     *
     * @param customer The Customer object to add.
     * @return The added Customer object.
     */
    public Customer addCustomer(Customer customer) {
        logger.info("Adding new customer: {}", customer.getId());
        customerRepository.add(customer);
        return customer;
    }

    /**
     * Updates an existing customer.
     *
     * @param id The UUID of the customer to update.
     * @param customerDetails The Customer object containing updated details.
     * @return The updated Customer object.
     * @throws ResourceNotFoundException if the customer is not found.
     */
    public Customer updateCustomer(UUID id, Customer customerDetails) {
        logger.info("Updating customer with ID: {}", id);
        Customer existingCustomer = customerRepository.get(id);
        if (existingCustomer == null) {
            logger.error("Customer not found with ID: {}", id);
            throw new ResourceNotFoundException("Customer not found");
        }
        // TO DO: decide if customer can also change their id

        if (customerDetails.getName() != null) {
            existingCustomer.setName(customerDetails.getName());
        }
        if (customerDetails.getEmail() != null) {
            existingCustomer.setEmail(customerDetails.getEmail());
        }
        if (customerDetails.getPhoneNumber() != null) {
            existingCustomer.setPhoneNumber(customerDetails.getPhoneNumber());
        }

        customerRepository.update(existingCustomer);
        return existingCustomer;
    }
    
    /**
     * Deletes a customer by its ID.
     *
     * @param id The UUID of the customer to delete.
     * @throws ResourceNotFoundException if the customer is not found.
     */
    public void deleteCustomer(UUID id) {
        logger.info("Deleting customer with ID: {}", id);
        Customer customer = customerRepository.get(id);
        if (customer == null) {
            logger.error("Customer not found with ID: {}", id);
            throw new ResourceNotFoundException("Customer not found");
        }
        customerRepository.remove(id);
    }
}
