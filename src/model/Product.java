package model;

import java.util.Date;
import java.util.stream.IntStream;

public class Product {
    private Integer ID;
    private String name;
    private Integer quantity;
    private Double price;
    private Boolean availability;

    public Product(String name, Integer quantity, Double price, Boolean availability) {
        this.name = name;
        if (quantity < 0) {
            //throw new Exception("Quantity less 0!");
        }
        this.quantity = quantity;
        this.price = price;
        this.availability = availability;
        this.ID = 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return name;
    }
}
