package lsit.Controllers;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    // access: server, manager
    @GetMapping("/orders/{id}")
    public Order getOrderById(@PathVariable("id") UUID id){
        return orderService.getOrderById(id);
    }

    // access: customer, server, manager
    @PostMapping("/orders")
    public Order addOrder(@RequestBody Order o){
        return orderService.addOrder(o.getCustomerId(), o.getOrderItemIds());
    }

    // access: server, manager
    @PutMapping("/orders/{id}")
    public Order updateOrder(@PathVariable("id") UUID id, @RequestBody Order o){
        return orderService.updateOrder(id, o.getCustomerId(), o.getOrderItemIds());
    }

    // access: server, manager
    @DeleteMapping("/orders/{id}")
    public void deleteOrder(@PathVariable("id") UUID id){
        orderService.deleteOrder(id);
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
