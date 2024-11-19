package lsit.Models;

import java.util.UUID;

public class MenuItem {
    public UUID id;
    public String name;
    public String description;
    public double price;
    public MealSize size;
    public String[] listOfAllergies;


    public enum MealSize {
        KIDS, NORMAL
    }
        
}
