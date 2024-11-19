package lsit.Controllers;


import java.util.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lsit.Models.Order;
import lsit.Repositories.OrderRepository;


@RestController
public class OrderController {
    
    OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    // access: server, manager
    @GetMapping("/orders")
    public List<Order> getAllOrders(){
        return orderRepository.list();
    }

    // access: server, manager
    @GetMapping("/orders/{id}")
    public Order getOrderById(@PathVariable("id") UUID id){
        return orderRepository.get(id);
    }

    // access: customer, server, manager
    @PostMapping("/orders")
    public Order addOrder(@RequestBody Order o){
        orderRepository.add(o);
        return o;
    }

    // access: server, manager
    @PutMapping("/orders/{id}")
    public Order updateOrder(@PathVariable("id") UUID id, @RequestBody Order o){
        o.id = id;
        orderRepository.update(o);
        return o;
    }

    // access: server, manager
    @DeleteMapping("/orders/{id}")
    public void deleteOrder(@PathVariable("id") UUID id){
        orderRepository.remove(id);
    }
}
