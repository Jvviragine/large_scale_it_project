package lsit.Models;

import java.util.UUID;

public class Pet {
    public UUID id;
    public String name;
    public PetKind kind;

    public enum PetKind{
        DOG, CAT
    }
}
