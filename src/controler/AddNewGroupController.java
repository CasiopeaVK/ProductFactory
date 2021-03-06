package controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import db.DBContext;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Product;
import model.ProductGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller window create new group
 */
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
    private ArrayList<CardController> cards;
    private ArrayList<Product> products;
    private DBContext dbContext;
    private ArrayList<Product> productsFromJson = new ArrayList<Product>();
    private ProductGroup selectedGroup = null;

    /**
     * @param groupPane
     * @param cards
     * @param products
     * @param dbContext
     */
    public AddNewGroupController(TilePane groupPane, ArrayList<CardController> cards, ArrayList<Product> products, DBContext dbContext) {
        this.groupPane = groupPane;
        this.cards = cards;
        this.products = products;
        this.dbContext = dbContext;

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

    /**
     * Method for choose photo
     */
    @FXML
    public void choosePhoto() {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());
        if (file == null) return;
        image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

    /**
     * @throws IOException
     * Method for save group
     */
    @FXML
    public void saveGroup() throws IOException {
        boolean uniqueName = true;
        for (CardController card : cards) {
            if (nameField.getText().equals(card.getName())) {
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

        if (imageView.getImage() == null || !nameField.validate()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You miss something!");
            alert.showAndWait();
            return;
        }
        ProductGroup productGroup = new ProductGroup(imageView.getImage(), nameField.getText(), descriptionField.getText());
        CardController cardController = new CardController(productGroup, cards, products, groupPane, dbContext);
        productGroup.setProducts(productsFromJson);
        groupPane.getChildren().add(cardController);
        cards.add(cardController);
        //TODO add to DB
        dbContext.addProductGroup(productGroup);
        //частина коду для закриття вікна
        Stage stage = (Stage) btn_confirm.getScene().getWindow();
        stage.close();
    }

    /**
     * @param event
     * Method for import from json
     */
    @FXML
    void importFromJson(ActionEvent event) {
        System.out.println("KEKE");
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());
        if (file == null) return;
        ProductGroup productGroup = null;
        try {
            productGroup = DBContext.getProductGroup(file);
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Wrong data!");
            alert.showAndWait();
        }
        productGroup.updateImage();
        selectedGroup = productGroup;
        imageView.setImage(productGroup.getGroupIcon());
        nameField.setText(productGroup.getName());
        descriptionField.setText(productGroup.getDescription());
        productsFromJson = productGroup.getProducts();
    }

    /**
     * @param textField
     * Add validation to form
     */
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
