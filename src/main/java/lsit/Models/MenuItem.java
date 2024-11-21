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

    // Default constructor
    public MenuItem() {
        this.id = UUID.randomUUID();
    }

    // Parameterized constructor
    public MenuItem(String name, String description, double price, MealSize size, String[] listOfAllergies) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.size = size;
        this.listOfAllergies = listOfAllergies;
    }

    // Getter for the 'id'
    public UUID getId() {
        return id;
    }
    // Setter for the 'id'
    public void setId(UUID id) {
        this.id = id;
    }
        
    // Getter for 'name'
    public String getName() {
        return name;
    }

    // Setter for 'name'
    public void setName(String name) {
        this.name = name;
    }

    // Getter for 'description'
    public String getDescription() {
        return description;
    }

    // Setter for 'description'
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for 'price'
    public double getPrice() {
        return price;
    }

    // Setter for 'price'
    public void setPrice(double price) {
        this.price = price;
    }

    // Getter for 'size'
    public MealSize getSize() {
        return size;
    }

    // Setter for 'size'
    public void setSize(MealSize size) {
        this.size = size;
    }

    // Getter for 'listOfAllergies'
    public String[] getListOfAllergies() {
        return listOfAllergies;
    }

    // Setter for 'listOfAllergies'
    public void setListOfAllergies(String[] listOfAllergies) {
        this.listOfAllergies = listOfAllergies;
    }
}
