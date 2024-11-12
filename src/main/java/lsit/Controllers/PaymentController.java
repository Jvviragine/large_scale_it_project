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

    @GetMapping("/payments")
    public List<Payment> list(){
        return paymentRepository.list();
    }

    @GetMapping("/payments/{id}")
    public Payment get(@PathVariable("id") UUID id){
        return paymentRepository.get(id);
    }

    @PostMapping("/payments")
    public Payment add(@RequestBody Payment p){
        paymentRepository.add(p);
        return p;
    }

    @PutMapping("/payments/{id}")
    public Payment update(@PathVariable("id") UUID id, @RequestBody Payment p){
        p.id = id;
        paymentRepository.update(p);
        return p;
    }

    @DeleteMapping("/payments/{id}")
    public void delete(@PathVariable("id") UUID id){
        paymentRepository.remove(id);
    }

}
