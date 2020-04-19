package controler;

import com.jfoenix.controls.JFXButton;

import db.DBContext;
import javafx.event.ActionEvent;


import db.DBContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import model.Product;
import model.ProductGroup;

import model.ProductGroup;

import model.Product;
import model.ProductGroup;
import model.ProductGroup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
    private ArrayList<CardController> cards;
    private ArrayList<Product> products;

    public CardController(Image image, String name, String description) throws IOException {
        group = new ProductGroup(image, name, description);
        setStartConfig();
    }

    public CardController(ProductGroup group, ArrayList<CardController> cards, ArrayList<Product> products) throws IOException {
        this.group = group;
        this.cards = cards;
        this.products = products;

        setStartConfig();
    }

    private void setStartConfig() throws IOException {
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
//        btn_refactor.setOnAction(actionEvent -> showEditWindow());
    }

    @FXML
    void openInfoWindow(ActionEvent event) {
        GroupInfoController groupInfoController = new GroupInfoController(group,products);
        Scene scene = new Scene(groupInfoController, 400, 300);
        Stage window = new Stage();
        window.setTitle("Info group:");
        window.setScene(scene);
        window.show();
    }



    @FXML
    void showEditWindow()  {
        EditWindowController editWindowController = new EditWindowController(group, imgGroup, cardLabel, cards);
        Scene scene = new Scene(editWindowController);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public String getName(){return group.getName();}
    public Double getPrice(){return group.getPrice();}
    public int getCountProducts(){return group.getCountProducts();}
    public ArrayList<Product> getProduct(){return group.getProduct();}
    public ProductGroup getGroup(){return group;}
}
