package db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.ProductGroup;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DBContext {
    private  File mainContext;
    private static ArrayList<File> dbFiles;
    private ArrayList<ProductGroup> productGroups;

    public DBContext(File mainContext) throws FileNotFoundException {
        this.mainContext = mainContext;
        this.dbFiles = getFilesFromJson();
    }

    public static ArrayList<File> getDbFiles() {
        return dbFiles;
    }

    private void writeDbFiles() throws IOException {
        FileWriter fileWriter = new FileWriter(this.mainContext);
        fileWriter.write(new Gson().toJson(dbFiles));
        fileWriter.close();
    }

    private ArrayList<File> getFilesFromJson() throws FileNotFoundException {
        FileReader fileReader = new FileReader(this.mainContext);
        return new Gson().fromJson(fileReader, new TypeToken<List<File>>() {
        }.getType());
    }

    public ArrayList<ProductGroup> getProductGroups(File productFile) throws FileNotFoundException {
        FileReader fileReader = new FileReader(this.mainContext);
        return new Gson().fromJson(fileReader, new TypeToken<List<ProductGroup>>() {
        }.getType());
    }

    public static void writeProductGroup(ProductGroup group) throws IOException {
        FileWriter fileWriter = new FileWriter(group.getSourceFile());
        fileWriter.write(new Gson().toJson(group));
        fileWriter.close();
    }

    public static ProductGroup getProductGroup(File file) throws FileNotFoundException {
        FileReader fileReader = new FileReader(file);
        ProductGroup group = new Gson().fromJson(fileReader, ProductGroup.class);
        group.setSourceFile(file);
        DBContext.dbFiles.add(file);
        return group;
    }
}
