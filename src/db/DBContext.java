package db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.ProductGroup;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DBContext {
    private File mainContext;
    private ArrayList<ProductGroup> productGroups;

    public DBContext(File mainContext) throws FileNotFoundException {
        this.mainContext = mainContext;
    }

    private void writeDbFiles() throws IOException {
        FileWriter fileWriter = new FileWriter(this.mainContext);
        fileWriter.write(new Gson().toJson(productGroups));
        fileWriter.close();
    }

    public ArrayList<ProductGroup> getProductGroups() throws IOException {
        FileReader fileReader = new FileReader(mainContext);
        ArrayList<ProductGroup> groups = new Gson().fromJson(fileReader, new TypeToken<ArrayList<ProductGroup>>() {
        }.getType());
        fileReader.close();
        return groups;
    }

    public void addProductGroup(ProductGroup productGroup) {
        this.productGroups.add(productGroup);
    }

    public void removeProductGroup(ProductGroup productGroup) {
        this.productGroups.remove(productGroup);
    }
}
