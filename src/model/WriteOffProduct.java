package model;

import java.util.Date;

public class WriteOffProduct {
    private Date date;
    private String name;
    private Integer quantity;
    private Double price;

    public WriteOffProduct(String name, Integer quantity, Double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public Double getCost() {
        return price;
    }

    public void setCost(Double price) {
        this.price = price;
    }
}
