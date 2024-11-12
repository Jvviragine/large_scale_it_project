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

    @GetMapping("/orders")
    public List<Order> list(){
        return orderRepository.list();
    }

    @GetMapping("/orders/{id}")
    public Order get(@PathVariable("id") UUID id){
        return orderRepository.get(id);
    }

    @PostMapping("/orders")
    public Order add(@RequestBody Order o){
        orderRepository.add(o);
        return o;
    }

    @PutMapping("/orders/{id}")
    public Order update(@PathVariable("id") UUID id, @RequestBody Order o){
        o.id = id;
        orderRepository.update(o);
        return o;
    }

    @DeleteMapping("/orders/{id}")
    public void delete(@PathVariable("id") UUID id){
        orderRepository.remove(id);
    }
}
