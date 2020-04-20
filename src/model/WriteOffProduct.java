package model;

import java.util.Date;

/**
 * Class for create writeOff product
 */
public class WriteOffProduct {
    private Date date;
    private String name;
    private Integer quantity;
    private Double price;

    /**
     * @param name
     * @param quantity
     * @param price
     */
    public WriteOffProduct(String name, Integer quantity, Double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.date = new Date();
    }

    /**
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return name
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
     * @return summary cost
     */
    public Double getCost() {
        return price;
    }

    /**
     * @param price
     */
    public void setCost(Double price) {
        this.price = price;
    }
}
