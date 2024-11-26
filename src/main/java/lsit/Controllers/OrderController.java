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
import org.springframework.web.bind.annotation.RestController;

import lsit.Exceptions.InvalidInputException;
import lsit.Exceptions.ResourceNotFoundException;
import lsit.Models.Order;
import lsit.Services.OrderService;


@RestController
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    // access: server, manager
    @GetMapping("/orders")
    @PreAuthorize("hasAnyRole('PIZZARIA_SERVER', 'PIZZARIA_MANAGER')")
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // access: server, manager
    @GetMapping("/orders/{id}")
    @PreAuthorize("hasAnyRole('PIZZARIA_SERVER', 'PIZZARIA_MANAGER')")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") UUID id){
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    // access: customer, server, manager
    @PostMapping("/orders")
    @PreAuthorize("hasAnyRole('PIZZARIA_SERVER', 'PIZZARIA_MANAGER', 'PIZZARIA_CUSTOMER')")
    public ResponseEntity<Order> addOrder(@RequestBody Order o){
        Order newOrder = orderService.addOrder(o.getCustomerId(), o.getOrderItemIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    // access: server, manager
    @PutMapping("/orders/{id}")
    @PreAuthorize("hasAnyRole('PIZZARIA_SERVER', 'PIZZARIA_MANAGER')")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") UUID id, @RequestBody Order o){
        Order updatedOrder = orderService.updateOrder(id, o.getCustomerId(), o.getOrderItemIds());
        return ResponseEntity.ok(updatedOrder);

    }

    // access: server, manager
    @DeleteMapping("/orders/{id}")
    @PreAuthorize("hasAnyRole('PIZZARIA_SERVER', 'PIZZARIA_MANAGER')")
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