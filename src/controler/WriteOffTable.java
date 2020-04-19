package controler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.WriteOffProduct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class WriteOffTable extends TableView {
    @FXML
    private TableView<WriteOffProduct> writeOffTable;

    @FXML
    private TableColumn<WriteOffProduct, Date> col_Date;

    @FXML
    private TableColumn<WriteOffProduct, String> col_Name;

    @FXML
    private TableColumn<WriteOffProduct, Integer> col_Quantity;

    @FXML
    private TableColumn<WriteOffProduct, Double> col_Cost;

    private ObservableList<WriteOffProduct> writeOffProductObservableList;

    public WriteOffTable(ArrayList<WriteOffProduct> writeOffProducts) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "../fxml/writeOffTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        initTable(writeOffProducts);
    }

    private void initTable(ArrayList<WriteOffProduct> writeOffProducts) {
        col_Date.setCellValueFactory(new PropertyValueFactory<WriteOffProduct, Date>("Date"));
        col_Name.setCellValueFactory(new PropertyValueFactory<WriteOffProduct, String>("Name"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<WriteOffProduct, Integer>("Quantity"));
        col_Cost.setCellValueFactory(new PropertyValueFactory<WriteOffProduct, Double>("Cost"));
        writeOffProductObservableList = FXCollections.observableArrayList(writeOffProducts);
        writeOffTable.setItems(writeOffProductObservableList);
    }

    public ObservableList<WriteOffProduct> getObservableList() {
        return writeOffProductObservableList;
    }


}
