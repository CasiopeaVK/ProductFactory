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

/**
 * product group class
 */
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

    /**
     * @param image
     * @param name
     * @param description
     */
    public ProductGroup(Image image, String name, String description) {
        this.groupImage = image;
        this.name = name;
        this.description = description;
        setImageCode(image);
    }

    /**
     * @param image
     */
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

    /**
     * @return product
     */
    public ArrayList<Product> getProduct() {
        return products;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return icon
     */
    public Image getGroupIcon() {
        return this.groupImage;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param image
     */
    public void setGroupIcon(Image image) {
        this.groupImage = image;
        setImageCode(image);
    }

    /**
     * for update image after import
     */
    public void updateImage() {
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(this.imageCode));
            this.groupImage = SwingFXUtils.toFXImage(image, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return products
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * @param product
     */
    public void addProduct(Product product) {
        this.products.add(product);
    }

    /**
     * @return price
     */
    public Double getPrice() {
        double price = 0;

        for (Product product : products) {
            price += product.getPrice() * product.getQuantity();
        }

        return price;
    }

    /**
     * @return count of product
     */
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

    /**
     * @param products
     */
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
