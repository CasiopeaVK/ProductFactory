package model;

import java.util.Date;
import java.util.stream.IntStream;

/**
 * Class product
 */
public class Product {
    private Integer ID;
    private String name;
    private Integer quantity;
    private Double price;
    private Boolean availability;

    /**
     * @param name
     * @param quantity
     * @param price
     * @param availability
     */
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


    /**
     * @return  name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return avaliability
     */
    public Boolean getAvailability() {
        return availability;
    }

    /**
     * @param availability
     */
    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    /**
     * @return id
     */
    public Integer getID() {
        return ID;
    }

    /**
     * @param ID
     */
    public void setID(Integer ID) {
        this.ID = ID;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return name;
    }
}
