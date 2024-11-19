package lsit.Models;

import java.util.UUID;

public class Order {
    public UUID id;
    public Customer customer;
    public MenuItem[] orderItems;
    
}

