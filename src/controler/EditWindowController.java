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
import java.util.ArrayList;

/**@author Stanislav Bohuta, Kozyr Vladislav, Izvostkin Danylo
 * Class for show edit window*/
public class EditWindowController extends AnchorPane {

    /**Card name field*/
    @FXML
    private JFXTextField nameField;

    /**Card description field*/
    @FXML
    private JFXTextArea descriptionField;

    /**Button to change photo*/
    @FXML
    private JFXButton btn_change;

    /**Button to delete group*/
    @FXML
    private JFXButton btn_delete;

    /**Button for save changes*/
    @FXML
    private JFXButton btn_save;

    /**Card image*/
    @FXML
    private ImageView imageField;

    /**Object of group product*/
    private ProductGroup group;

    /**Object of card photo*/
    private ImageView cardPhoto;

    /**Object of label card*/
    private Label cardLabel;

    private ArrayList<CardController> cards;
    /**@param group
     * @param cardPhoto
     * @param  cardLabel
     * constructor*/
    public EditWindowController(ProductGroup group, ImageView cardPhoto, Label cardLabel, ArrayList<CardController> cards) {
        this.cards = cards;
        loadFXml();
        initWindow(group, cardPhoto, cardLabel);
    }

    /**Load FXml file*/
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

    /**Initialization window*/
    private void initWindow(ProductGroup group, ImageView cardPhoto, Label cardLabel) {
        this.cardPhoto = cardPhoto;
        this.cardLabel = cardLabel;
        this.group = group;
        nameField.setText(group.getName());
        descriptionField.setText(group.getDescription());
        imageField.setImage(group.getGroupIcon());
        setValidation(nameField);
    }

    /**Create validation for textField*/
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

    /**Method for choose photo on press button*/
    @FXML
    public void choosePhoto() {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(imageField.getScene().getWindow());
        if (file == null) return;
        imageField.setImage(new Image(file.toURI().toString()));
    }

    /**Method for save change on press button*/
    @FXML
    public void saveChange() {

        boolean uniqueName = true;
        for(CardController card : cards){
            if(nameField.getText().equals(card.getName())){
                uniqueName = false;
                break;
            }

        }

        if (!uniqueName) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Not unique name!");
            alert.showAndWait();
            return;
        }

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
