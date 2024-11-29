package lsit.Repositories;

import java.util.UUID;
import org.springframework.stereotype.Repository;
import lsit.Models.Customer;

@Repository
public class CustomerRepository extends GenericS3Repository<Customer> {
    public CustomerRepository() {
        super("customers", Customer.class);
    }

    @Override
    protected void setItemId(Customer item) {
        if (item.getId() == null) {
            item.setId(UUID.randomUUID());
        }
    }

    @Override
    protected UUID getItemId(Customer item) {
        return item.getId();
    }
}