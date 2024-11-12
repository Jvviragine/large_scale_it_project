package lsit.Models;

import java.util.UUID;


public class Payment {

    public UUID id;
    public Boolean processed; // true if paid succesfuly , false if not
    public Double amount;

}
