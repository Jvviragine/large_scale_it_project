package lsit.Models;

import java.util.UUID;

public class Payment {

    private UUID id;
    private Boolean processed; // true if paid successfully, false if not
    private Double amount;
    private UUID orderId;

    // Default constructor
    public Payment() {
        this.id = UUID.randomUUID();
    }

    // Overloaded constructor
    public Payment(UUID orderId, boolean processed, double amount) {
        this.id = UUID.randomUUID(); // Generate a unique ID for each payment
        this.orderId = orderId;
        this.processed = processed;
        this.amount = amount;
    }

    // Getter for orderId
    public UUID getOrderId() {
        return orderId;
    }

    // Setter for orderId
    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    // Getter for id
    public UUID getPaymentId() {
        return id;
    }

    // Setter for id
    public void setPaymentId(UUID paymentId) {
        this.id = paymentId;
    }

    // Getter for processed
    public boolean getStatus() {
        return processed;
    }

    // Setter for processed
    public void setStatus(boolean processed) {
        this.processed = processed;
    }

    // Getter for amount
    public double getAmount() {
        return amount;
    }

    // Setter for amount
    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
    }
}
