package lsit.Repositories;
import java.util.*;
import org.springframework.stereotype.Repository;
import lsit.Models.MenuItem;


@Repository
public class MenuItemRepository extends GenericS3Repository<MenuItem> {
    public MenuItemRepository() {
        super("menuitems", MenuItem.class);
    }

    @Override
    protected void setItemId(MenuItem item) {
        if (item.getId() == null) {
            item.setId(UUID.randomUUID());
        }
    }

    @Override
    protected UUID getItemId(MenuItem item) {
        return item.getId();
    }
}
