package lsit.Repositories;

import java.util.*;

import org.springframework.stereotype.Repository;

import lsit.Models.Pet;

@Repository
public class PetRepository {
    static HashMap<UUID, Pet> pets = new HashMap<>();

    public void add(Pet p){
        p.id = UUID.randomUUID();
        pets.put(p.id, p);
    }

    public Pet get(UUID id){
        return pets.get(id);
    }

    public void remove(UUID id){
        pets.remove(id);
    }

    public void update(Pet p){
        Pet x = pets.get(p.id);
        x.name = p.name;
        x.kind = p.kind;
    }

    public List<Pet> list(){
        return new ArrayList<>(pets.values());
    }
}
