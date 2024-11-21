package lsit.Repositories;

import java.util.*;

import lsit.Models.Pet;

public interface IPetRepository {
    void add(Pet p);

    Pet get(UUID id);

    void remove(UUID id);

    void update(Pet p);

    List<Pet> list();
}
