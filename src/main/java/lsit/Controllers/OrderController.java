package lsit.Controllers;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
    public List<Order> getAllOrders(OAuth2AuthenticationToken authentication) throws Exception{
        var groups = (List<String>)authentication.getPrincipal().getAttribute("https://gitlab.org/claims/groups/owner");
        if(groups.contains("lsit-ken3239/roles/pizzeria/pizzaria_server") || 
        groups.contains("lsit-ken3239/roles/pizzeria/pizzaria_manager")){
            return orderService.getAllOrders();
        } else {
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }
    }

    // access: server, manager
    @GetMapping("/orders/{id}")
    public Order getOrderById(@PathVariable("id") UUID id, OAuth2AuthenticationToken authentication) throws Exception{
        var groups = (List<String>)authentication.getPrincipal().getAttribute("https://gitlab.org/claims/groups/owner");
        if(groups.contains("lsit-ken3239/roles/pizzeria/pizzaria_server") || 
        groups.contains("lsit-ken3239/roles/pizzeria/pizzaria_manager")){
            return orderService.getOrderById(id);
        } else {
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }   
    }

    // access: customer, server, manager
    @PostMapping("/orders")
    public Order addOrder(@RequestBody Order o, OAuth2AuthenticationToken authentication) throws Exception{
        var groups = (List<String>)authentication.getPrincipal().getAttribute("https://gitlab.org/claims/groups/owner");
        if(groups.contains("lsit-ken3239/roles/pizzeria/pizzaria_server") || 
        groups.contains("lsit-ken3239/roles/pizzeria/pizzaria_manager") || 
        groups.contains("lsit-ken3239/roles/pizzeria/pizzaria_customer")){
            return orderService.addOrder(o.getCustomerId(), o.getOrderItemIds());
        } else {
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }   
    }

    // access: server, manager
    @PutMapping("/orders/{id}")
    public Order updateOrder(@PathVariable("id") UUID id, @RequestBody Order o, OAuth2AuthenticationToken authentication) throws Exception{
        var groups = (List<String>)authentication.getPrincipal().getAttribute("https://gitlab.org/claims/groups/owner");
        if(groups.contains("lsit-ken3239/roles/pizzeria/pizzaria_server") || 
        groups.contains("lsit-ken3239/roles/pizzeria/pizzaria_manager")){
            return orderService.updateOrder(id, o.getCustomerId(), o.getOrderItemIds());
        } else {
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }   
    }

    // access: server, manager
    @DeleteMapping("/orders/{id}")
    public void deleteOrder(@PathVariable("id") UUID id, OAuth2AuthenticationToken authentication) throws Exception {
        var groups = (List<String>)authentication.getPrincipal().getAttribute("https://gitlab.org/claims/groups/owner");
        if(groups.contains("lsit-ken3239/roles/pizzeria/pizzaria_server") || 
        groups.contains("lsit-ken3239/roles/pizzeria/pizzaria_manager")){
            orderService.deleteOrder(id);
        } else {
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }   
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
