package controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.IntegerValidator;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField quantityField;

    @FXML
    private JFXTextField priceField;

    @FXML
    private JFXButton btn_addProduct;

    private ProductGroup productGroup;
    private ArrayList<Product> allProducts;

    public GroupInfoController(ProductGroup group, ArrayList<Product> products) {
        this.allProducts = products;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/window_group_info.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.productGroup = group;
        initializeTable(group);
        setValidation();

    }

    @FXML
    void addProduct(ActionEvent event) {
        System.out.println("KEK");
        if (isConsist()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("This name is used!");
            alert.showAndWait();
            return;
        }
        if (!nameField.validate() || !quantityField.validate() || !priceField.validate()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You miss something!");
            alert.showAndWait();
            return;
        }
        String name = nameField.getText();
        Integer quantity = Integer.parseInt(quantityField.getText());
        Double price = Double.parseDouble(priceField.getText());
        Product product = new Product(name, quantity, price, true);
        products.add(product);
    }

    @FXML
    public void deleteProducts(ActionEvent actionEvent) {
        List<Product> selectedItems = tableView.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm action!");
        alert.setHeaderText(null);
        alert.setContentText("Are you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            products.removeAll(selectedItems);
        }
    }

    @FXML
    public void showGroup(ActionEvent actionEvent) {
        System.out.println(new ArrayList<Product>(products));
    }

    private boolean isConsist(){
        for(Product product:allProducts){
            if(product.getName().equals(nameField.getText()))
                return true;
        }
        return false;
    }

    private void setValidation() {
        RequiredFieldValidator textValidator = new RequiredFieldValidator();
        textValidator.setMessage("No empty input!");
        nameField.getValidators().add(textValidator);
        nameField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                nameField.validate();
            }
        });

        IntegerValidator numValidator = new IntegerValidator();
        numValidator.setMessage("Not int num!");
        quantityField.getValidators().add(numValidator);
        quantityField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                quantityField.validate();
            }
        });

        DoubleValidator doubleValidator = new DoubleValidator();
        doubleValidator.setMessage("Not double value!");
        priceField.getValidators().add(doubleValidator);
        priceField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                priceField.validate();
            }
        });
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
            if (e.getNewValue() == null || e.getNewValue() < e.getOldValue()) {
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
