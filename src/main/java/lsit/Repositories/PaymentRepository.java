package lsit.Repositories;

import java.util.*;
import org.springframework.stereotype.Repository;
import lsit.Models.Payment;

@Repository
public class PaymentRepository extends GenericS3Repository<Payment>  {
    public PaymentRepository() {
        super("payments", Payment.class);
    }

    @Override
    protected void setItemId(Payment payment){
        if (payment.getPaymentId() == null){
            payment.setPaymentId(UUID.randomUUID());
        }
    }

    @Override
    protected UUID getItemId(Payment payment){
        return payment.getPaymentId();
    }
}
