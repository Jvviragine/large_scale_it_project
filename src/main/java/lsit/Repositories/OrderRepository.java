package lsit.Repositories;
import java.util.*;
import org.springframework.stereotype.Repository;
import lsit.Models.Order;


@Repository
public class OrderRepository extends GenericS3Repository<Order> {
    public OrderRepository() {
        super("orders", Order.class);
    }

    @Override
    protected void setItemId(Order item) {
        if (item.getId() == null) {
            item.setId(UUID.randomUUID());
        }
    }

    @Override
    protected UUID getItemId(Order item) {
        return item.getId();
    }
}
