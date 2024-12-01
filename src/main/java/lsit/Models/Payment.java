package lsit.Models;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Payment {

    public UUID orderId;
    public Boolean processed; // true if paid succesfuly , false if not
    public Double amount;
    public UUID paymentId;

    public Payment(UUID OrderId, UUID PaymentId, boolean processed, double amount){
        this.paymentId =  UUID.randomUUID();
        this.orderId = OrderId;
        this.processed = processed;
        this.amount = amount;
    }

    // get the order id
    public UUID getOrderId(){
        return orderId;
    }
    // get payment id
    public UUID getPaymentId(){
        return paymentId;
    }

    public void setPaymentId(UUID PaymentID){
        this.paymentId = PaymentID;
    }

    public boolean getStatus(){
        return processed;
    }

    public double getAmount(){
        return amount;
    }
    
    public void setAmount(double amount){
        this.amount = amount;
    }
}
