package lsit.Repositories;

import java.util.*;

import org.springframework.stereotype.Repository;

import lsit.Models.Payment;


@Repository
public class PaymentRepository {
    
    static HashMap<UUID, Payment> payments = new HashMap<>();

    public void add(Payment p){
        p.id = UUID.randomUUID();
        payments.put(p.id, p);
    }

    public Payment get(UUID id){
        return payments.get(id);
    }

    public void remove(UUID id){
        payments.remove(id);
    }

    public void update(Payment p){

        Payment x = payments.get(p.id);
        x.amount = p.amount;
        x.order = p.order;
    }

    public void process(Payment p){
        Payment x = payments.get(p.id);
        x.processed = true;
    }

    

    public List<Payment> list(){
        return new ArrayList<>(payments.values());
    }
    
}
