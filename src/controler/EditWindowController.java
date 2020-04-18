package controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Product;
import model.ProductGroup;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EditWindowController extends AnchorPane {

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextArea descriptionField;

    @FXML
    private JFXButton btn_change;

    @FXML
    private JFXButton btn_delete;

    @FXML
    private JFXButton btn_save;

    @FXML
    private ImageView imageField;

    private ProductGroup group;
    private ImageView cardPhoto;
    private Label cardLabel;

    public EditWindowController(ProductGroup group, ImageView cardPhoto, Label cardLabel) {
        loadFXml();
        initWindow(group, cardPhoto, cardLabel);
    }

    private void loadFXml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "../fxml/window_edit_group.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void initWindow(ProductGroup group, ImageView cardPhoto, Label cardLabel) {
        this.cardPhoto = cardPhoto;
        this.cardLabel = cardLabel;
        this.group = group;
        nameField.setText(group.getName());
        descriptionField.setText(group.getDescription());
        imageField.setImage(group.getGroupIcon());
        setValidation(nameField);
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

    @FXML
    public void choosePhoto() {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(imageField.getScene().getWindow());
        if (file == null) return;
        imageField.setImage(new Image(file.toURI().toString()));
    }

    @FXML
    public void saveChange() {
        if (imageField.getImage() == null || !nameField.validate()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You miss something!");
            alert.showAndWait();
            return;
        }

        group.setGroupIcon(imageField.getImage());
        group.setName(nameField.getText());
        group.setDescription(descriptionField.getText());

        cardPhoto.setImage(group.getGroupIcon());
        cardLabel.setText(group.getName());

        //частина коду для закриття вікна
        Stage stage = (Stage) btn_save.getScene().getWindow();
        stage.close();


    }
}
