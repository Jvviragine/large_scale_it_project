package lsit.Services;

import lsit.Exceptions.InvalidInputException;
import lsit.Exceptions.ResourceNotFoundException;
import lsit.Models.Payment;
import lsit.Repositories.CustomerRepository;
import lsit.Repositories.MenuItemRepository;
import lsit.Repositories.OrderRepository;
import lsit.Repositories.PaymentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service class for managing payments.
 */
@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    /**
     * Retrieves all payments.
     *
     * @return List of all payments.
     */
    public List<Payment> getAllPayments() {
        List<Payment> payments = paymentRepository.list();
        // logger.info("Retrieved {} payments", payments.size());
        return payments;
    }
    

    /**
     * Retrieves a payment by its ID.
     *
     * @param paymentId The UUID of the payment.
     * @return The Payment object if found.
     * @throws ResourceNotFoundException if the payment is not found.
     */
    public Payment getPaymentById(UUID paymentId) {
        logger.info("Fetching payment with ID: {}", paymentId);
        Payment payment = paymentRepository.get(paymentId);
        if (payment == null) {
            logger.error("Payment not found with ID: {}", paymentId);
            throw new ResourceNotFoundException("Could not find Payment with provided ID");
        }
        return payment;
    }

    /**
     * Adds a new payment.
     *
     * @param payment The payment object to add.
     * @return The added Payment object.
     * @throws InvalidInputException if the payment details are invalid.
     */
    public Payment addPayment(Payment payment) {
        logger.info("Adding new payment");
        if (payment == null) {
            logger.error("Invalid payment details provided");
            throw new InvalidInputException("Invalid payment details provided");
        }
        paymentRepository.add(payment);
        return payment;
    }

    /**
     * Updates an existing payment.
     *
     * @param paymentId The UUID of the payment to update.
     * @param updatedPayment The updated payment object.
     * @return The updated Payment object.
     * @throws ResourceNotFoundException if the payment is not found.
     * @throws InvalidInputException if the updated details are invalid.
     */
    public Payment updatePayment(UUID paymentId, Payment updatedPayment) {
        logger.info("Updating payment with ID: {}", paymentId);
        Payment existingPayment = paymentRepository.get(paymentId);
        if (existingPayment == null) {
            logger.error("Payment not found with ID: {}", paymentId);
            throw new ResourceNotFoundException("Payment not found with provided ID");
        }
        if (updatedPayment == null) {
            logger.error("Invalid updated payment details");
            throw new InvalidInputException("Invalid updated payment details");
        }

        updatedPayment.setPaymentId(paymentId);
        paymentRepository.update(updatedPayment);
        return updatedPayment;
    }

    /**
     * Deletes a payment by its ID.
     *
     * @param paymentId The UUID of the payment to delete.
     * @throws ResourceNotFoundException if the payment is not found.
     */
    public void deletePayment(UUID paymentId) {
        logger.info("Deleting payment with ID: {}", paymentId);
        Payment payment = paymentRepository.get(paymentId);
        if (payment == null) {
            logger.error("Payment not found with ID: {}", paymentId);
            throw new ResourceNotFoundException("Payment not found with provided ID");
        }
        paymentRepository.remove(paymentId);
    }
}
