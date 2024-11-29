package lsit.Controllers;

import java.util.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lsit.Models.Payment;
import lsit.Repositories.PaymentRepository;


@RestController
public class PaymentController {
    
    PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    // access: manager
    @GetMapping("/payments")
    public List<Payment> getAllPayments(){
        return paymentRepository.list();
    }

    // access: manager
    @GetMapping("/payments/{id}")
    public Payment getPaymentById(@PathVariable("id") UUID id){
        return paymentRepository.get(id);
    }

    // access: server, manager
    @PostMapping("/payments")
    public Payment addPayment(@RequestBody Payment p){
        paymentRepository.add(p);
        return p;
    }

    // access: server, manager
    @PutMapping("/payments/{id}")
    public Payment updatePayment(@PathVariable("id") UUID id, @RequestBody Payment p){
        p.id = id;
        paymentRepository.update(p);
        return p;
    }

    // access: manager
    @DeleteMapping("/payments/{id}")
    public void deletePayment(@PathVariable("id") UUID id){
        paymentRepository.remove(id);
    }

}
