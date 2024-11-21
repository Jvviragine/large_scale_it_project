package lsit.Repositories;

import java.util.*;

import lsit.Models.Customer;

public interface ICustomerRepository {
    void add(Customer c);

    Customer get(UUID id);

    void remove(UUID id);

    void update(Customer c);

    List<Customer> list();
    
}
