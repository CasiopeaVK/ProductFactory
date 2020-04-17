package model;

import java.util.Date;

public class Product {
    private String name;
    private Integer quantity;
    private Double price;
    private Boolean availability;
    private Date date;

    public Product(String name, Integer quantity, Double price, Boolean availability, Date date) throws Exception {
        this.name = name;
        if (quantity < 0) {
            throw new Exception("Quantity less 0!");
        }
        this.quantity = quantity;
        this.price = price;
        this.availability = availability;
        this.date = date;
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
}
