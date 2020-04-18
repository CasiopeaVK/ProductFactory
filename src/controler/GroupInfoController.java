package controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.Product;
import model.ProductGroup;

import java.io.IOException;
import java.util.ArrayList;

public class GroupInfoController extends SplitPane {

    @FXML
    private TableView<Product> tableView;

    @FXML
    private TableColumn<Product, Integer> col_Id;

    @FXML
    private TableColumn<Product, String> col_Name;

    @FXML
    private TableColumn<Product, Integer> col_Quantity;

    @FXML
    private TableColumn<Product, Double> col_Price;

    @FXML
    private TableColumn<Product, Boolean> col_Availability;

    @FXML
    private ObservableList<Product> products;

    @FXML
    private JFXTextField searchField;


    public GroupInfoController(ProductGroup group) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/window_group_info.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        initializeTable(group);
    }

    private void initializeTable(ProductGroup group) {
        initializeColumn();
        initializeFilterData(group);
    }

    private void initializeFilterData(ProductGroup group) {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setEditable(true);
        try {
            products = getDataFromGroup(group);
        } catch (NullPointerException ex) {
            products = FXCollections.observableList(new ArrayList<Product>());
        }


        FilteredList<Product> filteredData = new FilteredList<Product>(products, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Product> sortedData = new SortedList<Product>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);

    }

    private ObservableList<Product> getDataFromGroup(ProductGroup group) {
        return FXCollections.observableList(group.getProducts());
    }

    private void initializeColumn() {
        col_Id.setCellValueFactory(new PropertyValueFactory<Product, Integer>("ID"));
        col_Name.setCellValueFactory(new PropertyValueFactory<Product, String>("Name"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("Quantity"));
        col_Price.setCellValueFactory(new PropertyValueFactory<Product, Double>("Price"));
        col_Availability.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Product, Boolean> param) {
                Product product = param.getValue();

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

        col_Name.setCellFactory(TextFieldTableCell.forTableColumn());
        col_Name.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });

        col_Quantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter() {
            @Override
            public Integer fromString(String value) {
                try {
                    return super.fromString(value);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }));
        col_Quantity.setOnEditCommit(e -> {
            Product tmpProduct = e.getTableView().getItems().get(e.getTablePosition().getRow());
            if (e.getNewValue() == null) {
                tmpProduct.setQuantity(e.getOldValue());
                tableView.refresh();
                System.out.println("NULL");
                return;
            }
            tmpProduct.setQuantity(e.getNewValue());
        });

        col_Price.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() {
            @Override
            public Double fromString(String value) {
                try {
                    return super.fromString(value);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }));
        col_Price.setOnEditCommit(e -> {
            Product tmpProduct = e.getTableView().getItems().get(e.getTablePosition().getRow());
            if (e.getNewValue() == null) {
                tmpProduct.setPrice(e.getOldValue());
                tableView.refresh();
                System.out.println("NULL");
                return;
            }
            tmpProduct.setPrice(e.getNewValue());
        });

        col_Availability.setCellFactory(new Callback<TableColumn<Product, Boolean>, TableCell<Product, Boolean>>() {
                                            @Override
                                            public TableCell<Product, Boolean> call(TableColumn<Product, Boolean> productIntegerTableColumn) {
                                                CheckBoxTableCell<Product, Boolean> cell = new CheckBoxTableCell<Product, Boolean>();
                                                cell.setAlignment(Pos.CENTER);
                                                return cell;
                                            }
                                        }
        );
        col_Availability.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setAvailability(e.getNewValue());
        });
    }


}
