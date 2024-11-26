package lsit.Controllers;

import java.util.*;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('PIZZARIA_MANAGER')")
    public List<Payment> getAllPayments(){
        return paymentRepository.list();
    }

    // access: manager, server
    @GetMapping("/payments/{id}")
    @PreAuthorize("hasAnyRole('PIZZARIA_SERVER', 'PIZZARIA_MANAGER')")
    public Payment getPaymentById(@PathVariable("id") UUID PaymentId){
        return paymentRepository.get(PaymentId);
    }

    // access: server, manager
    @PostMapping("/payments")
    @PreAuthorize("hasAnyRole('PIZZARIA_SERVER', 'PIZZARIA_MANAGER')")
    public Payment addPayment(@RequestBody Payment p){
        paymentRepository.add(p);
        return p;
    }

    // access: manager
    @PutMapping("/payments/{id}")
    @PreAuthorize("hasAnyRole('PIZZARIA_MANAGER')")
    public Payment updatePayment(@PathVariable("id") UUID PaymentId, @RequestBody Payment p){
        p.setPaymentId(PaymentId);
        paymentRepository.update(p);
        return p;
    }

    // access: manager
    @DeleteMapping("/payments/{id}")
    @PreAuthorize("hasAnyRole('PIZZARIA_MANAGER')")
    public void deletePayment(@PathVariable("id") UUID PaymentId){
        paymentRepository.remove(PaymentId);
    }

}
