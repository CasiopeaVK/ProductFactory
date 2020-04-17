package controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.TilePane;

import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private JFXButton btn_addGroup;

    @FXML
    private JFXRadioButton rb_alphabet;

    @FXML
    private ToggleGroup group;

    @FXML
    private JFXRadioButton rb_cost;

    @FXML
    private JFXRadioButton rb_count;

    @FXML
    private ScrollPane groupScrollPane;

    @FXML
    private TilePane groupTilePane;

    @FXML
    private TableView<?> writeOffTable;

    @FXML
    private TableColumn<?, ?> dataColumn;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> countColumn;

    @FXML
    private TableColumn<?, ?> costColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       groupScrollPane.widthProperty().addListener((observableValue, number, t1) -> groupTilePane.setPrefWidth(groupScrollPane.getWidth()));

    }


    public void addGroup(ActionEvent actionEvent) {
        AddNewGroupController groupController = new AddNewGroupController();
        Scene scene = new Scene(groupController, 400, 300);
        Stage window = new Stage();
        window.setTitle("Add new group:");
        window.setScene(scene);
        window.show();
    }
}
