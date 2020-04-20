package controler;

import com.jfoenix.controls.JFXButton;
import db.DBContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.Product;
import model.ProductGroup;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for control cards
 */
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
    private TilePane groupTilePane;
    private DBContext dbContext;

    /**
     * @param image
     * @param name
     * @param description
     * @param dbContext
     * @throws IOException
     */
    public CardController(Image image, String name, String description, DBContext dbContext) throws IOException {
        group = new ProductGroup(image, name, description);
        this.dbContext = dbContext;
        setStartConfig();
    }

    /**
     * @param group
     * @param cards
     * @param products
     * @param groupTilePane
     * @param dbContext
     * @throws IOException
     */
    public CardController(ProductGroup group, ArrayList<CardController> cards, ArrayList<Product> products, TilePane groupTilePane, DBContext dbContext) throws IOException {
        this.group = group;
        this.cards = cards;
        this.products = products;
        this.groupTilePane = groupTilePane;
        this.dbContext = dbContext;
        setStartConfig();
    }

    /**
     * @throws IOException
     * Set start configuration
     */
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

    /**
     * @param event
     */
    @FXML
    void openInfoWindow(ActionEvent event) {
        GroupInfoController groupInfoController = new GroupInfoController(group, products);
        Scene scene = new Scene(groupInfoController, 400, 300);
        Stage window = new Stage();
        groupInfoController.setStage(window);
        window.setTitle("Info group:");
        window.setScene(scene);
        window.show();
    }


    /**
     *
     */
    @FXML
    void showEditWindow() {
        EditWindowController editWindowController = new EditWindowController(group, imgGroup, cardLabel, cards, this);
        Scene scene = new Scene(editWindowController);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @return
     * return name
     */
    public String getName() {
        return group.getName();
    }

    /**
     * @return price
     */
    public Double getPrice() {
        return group.getPrice();
    }

    /**
     * @return count of products
     */
    public int getCountProducts() {
        return group.getCountProducts();
    }

    /**
     * @return group of products
     */
    public ArrayList<Product> getProduct() {
        return group.getProduct();
    }

    /**
     * @return group
     */
    public ProductGroup getGroup() {
        return group;
    }

    /**
     * @return tile pane
     */
    public TilePane getTilePane() {
        return this.groupTilePane;
    }

    /**
     * @return db context
     */
    public DBContext getDbContext() {
        return dbContext;
    }
}
