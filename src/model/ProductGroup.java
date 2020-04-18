package model;

import db.DBContext;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class ProductGroup {
    private Image groupIcon;
    private String name;
    private String description;
    private File sourceFile;
    private ArrayList<Product> products;

    public ProductGroup(Image groupIcon, String name, String description) {
        this.groupIcon = groupIcon;
        this.name = name;
        this.description = description;
        this.sourceFile = new File(name + ".json");
    }

    public static ProductGroup getProductGroup(File file) throws FileNotFoundException {
        return DBContext.getProductGroup(file);
    }

    public String getName() {
        return name;
    }

    public Image getGroupIcon() {
        return groupIcon;
    }

    public File getSourceFile() {
        return this.sourceFile;
    }

    public void setSourceFile(File file) {
        this.sourceFile = sourceFile;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
