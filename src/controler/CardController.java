package controler;

import com.jfoenix.controls.JFXButton;

import db.DBContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Product;
import model.ProductGroup;
import java.io.IOException;

public class CardController extends Pane {


    @FXML
    private ImageView imgGroup;

    @FXML
    private JFXButton btn_info;

    @FXML
    private JFXButton btn_refactor;

    @FXML
    private Label cardLabel;

    private ProductGroup group;

    public CardController(Image groupIcon, String name, String description){
        group = new ProductGroup(groupIcon,name,description);
    }
    public CardController(ProductGroup group){
        this.group = group;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "../fxml/card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        cardLabel.setText(group.getName());
        imgGroup.setImage(group.getGroupIcon());
    }

    @FXML
    void openInfoWindow(ActionEvent event) {
        GroupInfoController groupInfoController = new GroupInfoController(group);
        Scene scene = new Scene(groupInfoController, 400, 300);
        Stage window = new Stage();
        window.setTitle("Info group:");
        window.setScene(scene);
        window.show();
    }
}
