package controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ProductGroup;

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

    private Image image;
    private TilePane groupPane;

    public AddNewGroupController(TilePane groupPane) {
        this.groupPane = groupPane;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/window_add_new_group.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        setValidation(nameField);
    }

    @FXML
    public void choosePhoto() {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());
        if(file == null) return;
        image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

    @FXML
<<<<<<< HEAD
    public void saveGroup() {
        if(imageView.getImage() == null || !nameField.validate()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You miss something!");
            alert.showAndWait();
            return;
        }
        CardController cardController = new CardController(image, nameField.getText(), descriptionField.getText());
=======
    public void saveGroup(){
        ProductGroup productGroup = new ProductGroup(image,nameField.getText(),descriptionField.getText());
        CardController cardController = new CardController(productGroup);
>>>>>>> 7e5a61ebd61e941e8d85c13a991fe636b2a14318
        groupPane.getChildren().add(cardController);

        //TODO add to DB
        //частина коду для закриття вікна
        Stage stage = (Stage) btn_confirm.getScene().getWindow();
        stage.close();
    }

    public void setValidation(JFXTextField textField) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        textField.getValidators().add(validator);
        validator.setMessage("No empty input!");
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean newValue) {
                if (!newValue) {
                    textField.validate();
                }
            }
        });
    }
}
