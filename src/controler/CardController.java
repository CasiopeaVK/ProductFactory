package controler;

import com.jfoenix.controls.JFXButton;

import db.DBContext;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.Product;
import model.ProductGroup;

import java.awt.*;
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
<<<<<<< HEAD

    public CardController(Image groupIcon, String name, String description){
        group = new ProductGroup(groupIcon,name,description);
=======
    public CardController(ProductGroup group){
        this.group = group;
>>>>>>> 7e5a61ebd61e941e8d85c13a991fe636b2a14318

//        try {
//            DBContext.writeProductGroup(group);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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
}
