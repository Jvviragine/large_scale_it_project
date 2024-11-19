package lsit.Repositories;
import java.util.*;
import org.springframework.stereotype.Repository;
import lsit.Models.MenuItem;


@Repository
public class MenuItemRepository {
    static HashMap<UUID, MenuItem> menu = new HashMap<>();

    public void add(MenuItem i){
        i.id = UUID.randomUUID();
        menu.put(i.id, i);
    }

    public MenuItem get(UUID id){
        return menu.get(id);
    }

    public void remove(UUID id){
        menu.remove(id);
    }

    public void update(MenuItem i){
        MenuItem x = menu.get(i.id);
        x.name = i.name;
        x.size = i.size;
    }

    public List<MenuItem> list(){
        return new ArrayList<>(menu.values());
    }
}
