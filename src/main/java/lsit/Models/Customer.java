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
    
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;

    // Default constructor
    public Customer() {
        this.id = UUID.randomUUID();
    }

    // Parameterized constructor
    public Customer(String name, String email, String phoneNumber) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
