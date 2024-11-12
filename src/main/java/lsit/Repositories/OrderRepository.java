package lsit.Repositories;


import java.util.*;

import org.springframework.stereotype.Repository;

import lsit.Models.Order;


@Repository
public class OrderRepository {
    static HashMap<UUID, Order> orders = new HashMap<>();

    public void add(Order o){
        o.id = UUID.randomUUID();
        orders.put(o.id, o);
    }

    public Order get(UUID id){
        return orders.get(id);
    }

    public void remove(UUID id){
        orders.remove(id);
    }

    public void update(Order o){
        Order x = orders.get(o.id);
        x.customer = o.customer;
        x.orderItems = o.orderItems;
    }

    public List<Order> list(){
        return new ArrayList<>(orders.values());
    }
}
