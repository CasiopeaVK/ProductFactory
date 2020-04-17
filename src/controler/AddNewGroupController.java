package controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class AddNewGroupController extends AnchorPane {
    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextArea descriptionField;

    @FXML
    private JFXButton btn_add_photo;

    @FXML
    private JFXButton btn_confirm;

    @FXML
    private ImageView imageView;

    @FXML
    private JFXButton btn_import;
    private  Image image;

    public AddNewGroupController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/window_add_new_group.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void choosePhoto(){
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());
        image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

    @FXML
    public void saveGroup(){
        CardController cardController = new CardController(image,nameField.getText(),descriptionField.getText());
        System.out.println(2);
    }
}
