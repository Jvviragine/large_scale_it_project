package lsit.Controllers;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lsit.Exceptions.InvalidInputException;
import lsit.Exceptions.ResourceNotFoundException;
import lsit.Models.Order;
import lsit.Services.OrderService;


@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    // access: server, manager
    @GetMapping
    @PreAuthorize("hasAnyRole('PIZZERIA_SERVER', 'PIZZERIA_MANAGER')")
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // access: server, manager
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PIZZERIA_SERVER', 'PIZZERIA_MANAGER')")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") UUID id){
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    // access: customer, server, manager
    @PostMapping
    @PreAuthorize("hasAnyRole('PIZZERIA_SERVER', 'PIZZERIA_MANAGER', 'PIZZERIA_CUSTOMER')")
    public ResponseEntity<Order> addOrder(@RequestBody Order o){
        Order newOrder = orderService.addOrder(o.getCustomerId(), o.getOrderItemIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    // access: server, manager
    @PutMapping
    @PreAuthorize("hasAnyRole('PIZZERIA_SERVER', 'PIZZERIA_MANAGER')")
    public ResponseEntity<Order> updateOrder(@RequestBody Order orderDetails){
        Order updatedOrder = orderService.updateOrder(orderDetails);
        return ResponseEntity.ok(updatedOrder);
    }   

    // access: server, manager
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PIZZERIA_SERVER', 'PIZZERIA_MANAGER')")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") UUID id){
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}