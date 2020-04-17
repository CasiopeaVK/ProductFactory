package controler;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private JFXButton btn_addGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addGroup(ActionEvent actionEvent) {
        System.out.println("KEK");
        AddNewGroupController groupController = new AddNewGroupController();
        Scene scene = new Scene(groupController, 400, 300);
        Stage window = new Stage();
        window.setTitle("Add new group:");
        window.setScene(scene);
        window.show();
    }
}
