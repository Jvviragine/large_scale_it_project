package lsit.Models;

import java.util.UUID;

/**
 * Represents a customer in the system.
 * 
 * Assumptions:
 * - Each customer has a unique UUID.
 * - Email addresses are unique.
 * - Phone numbers follow a standard format.
 */
public class Customer {
    
    // Unique identifier for the customer
    private UUID id;
    
    // Full name of the customer
    private String name;
    
    // Unique email address of the customer
    private String email;
    
    // Contact phone number of the customer
    private String phoneNumber;

    /**
     * Default constructor.
     * Initializes the customer with a unique UUID.
     */
    public Customer() {
        this.id = UUID.randomUUID();
    }

    /**
     * Parameterized constructor.
     * 
     * @param name         The full name of the customer.
     * @param email        The unique email address of the customer.
     * @param phoneNumber  The contact phone number of the customer.
     */
    public Customer(String name, String email, String phoneNumber) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getter for 'id'
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the customer.
     * 
     * @param id The UUID to set for the customer.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    // Getter and Setter for 'name'
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for 'email'
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Getter and Setter for 'phoneNumber'
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
