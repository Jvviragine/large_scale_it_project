package lsit.Controllers;

import java.util.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lsit.Models.Pet;
import lsit.Repositories.IPetRepository;

@RestController
public class PetContoller {

    IPetRepository petRepository;

    public PetContoller(IPetRepository petRepository){
        this.petRepository = petRepository;
    }

    // access: customer, server, manager
    @GetMapping("/pets")
    public List<Pet> list(){
        return petRepository.list();
    }

    @GetMapping("/pets/{id}")
    public Pet get(@PathVariable("id") UUID id){
        return petRepository.get(id);
    }

    @PostMapping("/pets")
    public Pet add(@RequestBody Pet p){
        petRepository.add(p);
        return p;
    }

    @PutMapping("/pets/{id}")
    public Pet update(@PathVariable("id") UUID id, @RequestBody Pet p){
        p.id = id;
        petRepository.update(p);
        return p;
    }

    @DeleteMapping("/pets/{id}")
    public void delete(@PathVariable("id") UUID id){
        petRepository.remove(id);
    }
}
