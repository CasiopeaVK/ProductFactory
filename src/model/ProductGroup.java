package model;

import db.DBContext;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

public class ProductGroup {
    private File groupIcon;
    private String name;
    private String description;
    private ArrayList<Product> products = new ArrayList<Product>();

    public ProductGroup(File groupIcon, String name, String description) {
        this.groupIcon = groupIcon;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Image getGroupIcon() throws IOException {
        return new Image(this.groupIcon.toURI().toString());
    }

    public String getDescription() {
        return this.description;
    }

    public void setGroupIcon(Image image) {
        this.groupIcon = new File(image.getUrl());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }
}
