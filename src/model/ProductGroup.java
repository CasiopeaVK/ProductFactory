package model;

import com.google.gson.annotations.Expose;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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

    public ArrayList<Product> getProduct() {
        return products;
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

    public Double getPrice() {
        double price = 0;

        for (Product product : products) {
            price += product.getPrice() * product.getQuantity();
        }

        return price;
    }

    public int getCountProducts() {
        int count = 0;
        for (Product product : products) {
            count += product.getQuantity();
        }
        return count;
    }

    public String toString() {
        return name;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
