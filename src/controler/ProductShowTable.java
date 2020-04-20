package controler;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;
import model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for table off all products
 */
public class ProductShowTable extends TreeTableView {

    @FXML
    private TreeTableView<Product> showTable;

    @FXML
    private TreeTableColumn<Product, String> col_Name;

    @FXML
    private TreeTableColumn<Product, Integer> col_Quantity;

    @FXML
    private TreeTableColumn<Product, Double> col_Price;

    @FXML
    private TreeTableColumn<Product, Boolean> col_Availability;

    /**
     * @param productInfo
     */
    public ProductShowTable(HashMap<String, ArrayList<Product>> productInfo) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "../fxml/window_product_show.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        initTable();
        createTreeTable(productInfo);
    }

    /**
     * @param productInfo
     */
    private void createTreeTable(HashMap<String, ArrayList<Product>> productInfo) {
        Double totalCost = 0.0;
        TreeItem<Product> groups = new TreeItem<>(new Product("Groups:", productInfo.size(), 0.0, true));
        for (Map.Entry<String, ArrayList<Product>> entry : productInfo.entrySet()) {
            Double groupPrice = 0.0;
            TreeItem<Product> root = new TreeItem<Product>(new Product(entry.getKey(), entry.getValue().size(), 0.0, true));
            for (Product product : entry.getValue()) {
                TreeItem<Product> productItem = new TreeItem<Product>(product);
                root.getChildren().add(productItem);
                groupPrice += product.getPrice() * product.getQuantity();
            }
            root.getValue().setPrice(groupPrice);
            totalCost += groupPrice;
            groups.getChildren().add(root);
        }
        groups.getValue().setPrice(totalCost);
        showTable.setRoot(groups);
    }

    /**
     * Initialize table
     */
    private void initTable() {
        col_Name.setCellValueFactory(new TreeItemPropertyValueFactory<Product, String>("Name"));
        col_Quantity.setCellValueFactory(new TreeItemPropertyValueFactory<Product, Integer>("Quantity"));
        col_Price.setCellValueFactory(new TreeItemPropertyValueFactory<Product, Double>("Price"));
        col_Availability.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Product, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<Product, Boolean> param) {
                Product product = param.getValue().getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(product.getAvailability());
                booleanProp.addListener(new ChangeListener<Boolean>() {

                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        product.setAvailability(newValue);
                    }
                });
                return booleanProp;
            }
        });

    }
}
