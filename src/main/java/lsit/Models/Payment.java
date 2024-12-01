package lsit.Models;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Payment {

    public UUID OrderId;
    public Boolean processed; // true if paid succesfuly , false if not
    public Double amount;
    public UUID PaymentId;

    public Payment(UUID OrderId, UUID PaymentId, boolean processed, double amount){
        this.PaymentId =  UUID.randomUUID();
        this.OrderId = OrderId;
        this.processed = processed;
        this.amount = amount;
    }

    // get the order id
    public UUID getOrderId(){
        return OrderId;
    }
    // get payment id
    public UUID getPaymentId(){
        return PaymentId;
    }

    public void setPaymentId(UUID PaymentID){
        this.PaymentId = PaymentID;
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
