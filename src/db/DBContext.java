package db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Product;
import model.ProductGroup;

import java.io.*;
import java.util.ArrayList;

/**
 * Main database
 */
public class DBContext {
    private File mainContext;
    private ArrayList<ProductGroup> productGroups;

    /**
     * @param mainContext
     * @throws FileNotFoundException
     */
    public DBContext(File mainContext) throws FileNotFoundException {
        this.mainContext = mainContext;
        try {
            this.productGroups = getProductGroups();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @throws IOException
     * Write information to file
     */
    public void writeDbFile() throws IOException {
        FileWriter fileWriter = new FileWriter(this.mainContext);
        fileWriter.write(new Gson().toJson(productGroups));
        fileWriter.close();
    }

    /**
     * @return group of products
     */
    public ArrayList<ProductGroup> getLoadedProductGroups() {
        return this.productGroups;
    }

    /**
     * @return
     * @throws IOException
     */
    public ArrayList<ProductGroup> getProductGroups() throws IOException {
        FileReader fileReader = new FileReader(mainContext);
        ArrayList<ProductGroup> groups = new Gson().fromJson(fileReader, new TypeToken<ArrayList<ProductGroup>>() {
        }.getType());
        fileReader.close();
        this.productGroups = groups;
        return groups;
    }

    /**
     * @param productGroup
     */
    public void addProductGroup(ProductGroup productGroup) {
        this.productGroups.add(productGroup);
    }

    /**
     * @param productGroup
     */
    public void removeProductGroup(ProductGroup productGroup) {
        this.productGroups.remove(productGroup);
    }

    /**
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static ProductGroup getProductGroup(File file) throws FileNotFoundException {
        FileReader fileReader = new FileReader(file);
        ArrayList<ProductGroup> productGroup = new Gson().fromJson(fileReader, new TypeToken<ArrayList<ProductGroup>>() {
        }.getType());
        return productGroup.get(0);
    }
}
