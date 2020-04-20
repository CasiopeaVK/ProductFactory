package db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.ProductGroup;
import model.WriteOffProduct;

import java.io.*;
import java.util.ArrayList;

/**
 * Main database
 */
public class DBContext {
    private File mainContext;
    private File historyContext;
    private ArrayList<ProductGroup> productGroups;
    private ArrayList<WriteOffProduct> writeOffProducts;

    /**
     * @param mainContext
     * @throws FileNotFoundException
     */
    public DBContext(File mainContext, File historyContext) throws FileNotFoundException {
        this.mainContext = mainContext;
        this.historyContext = historyContext;
        try {
            this.productGroups = getProductGroups();
            this.writeOffProducts = getWriteOffProducts();
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
        FileWriter historyWriter = new FileWriter(this.historyContext);
        historyWriter.write(new Gson().toJson(writeOffProducts));
        historyWriter.close();
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
     * @param
     */
    public ArrayList<WriteOffProduct> getWriteOffProducts() throws FileNotFoundException {
        FileReader fileReader = new FileReader(historyContext);
        ArrayList<WriteOffProduct> groups = new Gson().fromJson(fileReader, new TypeToken<ArrayList<WriteOffProduct>>() {
        }.getType());
        this.writeOffProducts = groups;
        return groups;
    }

    public void addProductGroup(ProductGroup productGroup) {
        this.productGroups.add(productGroup);
    }

    /**
     * @param
     */
    public void addWriteOffProduct(WriteOffProduct writeOffProduct){
        this.writeOffProducts.add(writeOffProduct);
    }

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
