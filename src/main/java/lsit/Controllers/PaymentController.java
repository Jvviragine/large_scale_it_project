package lsit.Controllers;

import lsit.Exceptions.ResourceNotFoundException;
import lsit.Models.Payment;
import lsit.Services.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for handling Payment-related HTTP requests.
 */
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * Retrieves all payments. Access: manager
     *
     * @return List of all payments.
     */
    @GetMapping("/payments")
    @PreAuthorize("hasRole('PIZZERIA_MANAGER')")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

        /**
     * Retrieves a payment by its UUID.
     *
     * @param id The UUID of the payment.
     * @return The Payment object if found.
     */
    @GetMapping("/payments/{id}")
    @PreAuthorize("hasAnyRole('PIZZERIA_SERVER', 'PIZZERIA_MANAGER')")
    public ResponseEntity<Payment> getPaymentById(@PathVariable UUID id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }


    /**
     * Adds a new payment. Access: server, manager
     *
     * @param payment The payment details.
     * @return The added Payment object.
     */
    @PostMapping("/payments")
    @PreAuthorize("hasAnyRole('PIZZERIA_SERVER', 'PIZZERIA_MANAGER')")
    public ResponseEntity<Payment> addPayment(@RequestBody Payment payment) {
        Payment newPayment = paymentService.addPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPayment);
    }

    /**
     * Updates an existing payment. Access: manager
     *
     * @param id The UUID of the payment to update.
     * @param paymentDetails The updated payment details.
     * @return The updated Payment object.
     */
    @PutMapping("/payments/{id}")
    @PreAuthorize("hasRole('PIZZERIA_MANAGER')")
    public ResponseEntity<Payment> updatePayment(@PathVariable UUID id, @RequestBody Payment paymentDetails) {
        Payment updatedPayment = paymentService.updatePayment(id, paymentDetails);
        return ResponseEntity.ok(updatedPayment);
    }

    /**
     * Deletes a payment by its UUID. Access: manager
     *
     * @param id The UUID of the payment to delete.
     * @return 200 OK if deleted.
     */
    @DeleteMapping("/payments/{id}")
    @PreAuthorize("hasRole('PIZZERIA_MANAGER')")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Handles ResourceNotFoundException and returns 404 status with message.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
