package model;

import com.google.gson.annotations.Expose;
import db.DBContext;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

public class ProductGroup {
    @Expose
    private byte[] imageCode;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private ArrayList<Product> products = new ArrayList<Product>();

    private transient Image groupImage;

    public ProductGroup(Image image, String name, String description) {
        this.groupImage = image;
        this.name = name;
        this.description = description;
        setImageCode(image);
    }

    private void setImageCode(Image image) {
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", s);
            this.imageCode = s.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public Image getGroupIcon() {
        return this.groupImage;
    }

    public String getDescription() {
        return this.description;
    }

    public void setGroupIcon(Image image) {
        this.groupImage = image;
        setImageCode(image);
    }

    public void updateImage() {
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(this.imageCode));
            this.groupImage = SwingFXUtils.toFXImage(image, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
