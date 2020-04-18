package controler;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSlider;
import db.DBContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;

import javafx.stage.Stage;
import model.Product;
import model.ProductGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private JFXComboBox<ProductGroup> chooseProductGroup;

    @FXML
    private JFXComboBox<Product> chooseProduct;

    @FXML
    private JFXSlider chooseSlider;

    @FXML
    private JFXButton btn_addGroup;

    @FXML
    private JFXRadioButton rb_alphabet;

    @FXML
    private ToggleGroup group;

    @FXML
    private JFXRadioButton rb_cost;

    @FXML
    private JFXRadioButton rb_count;

    @FXML
    private ScrollPane groupScrollPane;

    @FXML
    private TilePane groupTilePane;

    @FXML
    private TableView<?> writeOffTable;

    @FXML
    private TableColumn<?, ?> dataColumn;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> countColumn;

    @FXML
    private TableColumn<?, ?> costColumn;

    private DBContext dbContext;
    private ArrayList<CardController> cards = new ArrayList<CardController>();

    private ObservableList<ProductGroup> listGroup = FXCollections.observableArrayList();
    private ObservableList<Product> listProduct = FXCollections.observableArrayList();

    private ProductGroup currentGroup;
    private Product currentProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupScrollPane.widthProperty().addListener((observableValue, number, t1) -> groupTilePane.setPrefWidth(groupScrollPane.getWidth()));
        ArrayList<ProductGroup> productGroups = new ArrayList<ProductGroup>();
        try {
            dbContext = new DBContext(new File("C:\\Users\\Lenovo\\IdeaProjects\\ProductFactory\\src\\db\\DB.json"));
            productGroups = dbContext.getProductGroups();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (ProductGroup productGroup : productGroups) {
            productGroup.updateImage();
            try {
                addGroupToCanvas(productGroup);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            test();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(productGroups);

        chooseProductGroup.setItems(listGroup);
    }

    private void test() throws IOException {
        File file = new File("C:\\Users\\vladk\\IdeaProjects\\ExcelTest\\src\\grecha.jpg");
        Image image = new Image("grecha.jpg");
        ArrayList<ProductGroup> groups = new ArrayList<ProductGroup>();
        for (int i = 0; i < 20; i++) {
            ProductGroup productGroup = new ProductGroup(image, "EKE" + i, "norm");
            for (int j = 0; j < 20; j++) {
                String name = "Name - " + j;
                double price = 1 + j*4;
                int quantity = 1 + j*2;

                Product product = new Product(name, quantity, price, true);
                productGroup.addProduct(product);
            }
            groups.add(productGroup);
        }
        FileWriter fileWriter = new FileWriter("DB.json");
        fileWriter.write(new Gson().toJson(groups, new TypeToken<ArrayList<Product>>() {
        }.getType()));
        fileWriter.close();
    }

    private void addGroupToCanvas(ProductGroup group) throws IOException {
        CardController cardController = new CardController(group);
        groupTilePane.getChildren().add(cardController);
        cards.add(cardController);
        listGroup.add(cardController.getGroup());
    }

    @FXML
    public void addNewGroup(ActionEvent actionEvent) {
        AddNewGroupController groupController = new AddNewGroupController(groupTilePane,cards);
        Scene scene = new Scene(groupController, 400, 300);
        Stage window = new Stage();
        window.setTitle("Add new group:");
        window.setScene(scene);
        window.show();
    }


    public void addCardsGroupToCanvas(){
        for(CardController card:cards)
            groupTilePane.getChildren().add(card);
    }

    public void removeAllCardsFromCanvas(){
        for(CardController card : cards){
            groupTilePane.getChildren().remove(card);
        }
    }

    @FXML
    public void sortCardByAlphabet(){
        removeAllCardsFromCanvas();

        cards.sort((x,y) -> {
            String xs = x.getName();
            String ys = y.getName();
            return xs.compareTo(ys);
        });

        addCardsGroupToCanvas();
    }

    @FXML
    public void sortCardByCost(){
        removeAllCardsFromCanvas();

        cards.sort((x,y) -> (int) (y.getPrice() - x.getPrice()));

        addCardsGroupToCanvas();
    }

    @FXML
    public void sortByCountProducts(){
        removeAllCardsFromCanvas();
        cards.sort((x,y) -> y.getCountProducts() - x.getCountProducts());
        addCardsGroupToCanvas();
    }

    @FXML
    public void chooseProductGroupListener(){
        currentGroup = chooseProductGroup.getValue();

        ArrayList<Product> products = currentGroup.getProduct();

        for(Product product:products){
            listProduct.add(product);
        }

        chooseProduct.setItems(listProduct);
    }

    @FXML
    public void chooseProductListener(){
        currentProduct = chooseProduct.getValue();
        chooseSlider.setMin(1);
        chooseSlider.setMax(currentProduct.getQuantity());
        chooseSlider.setValue(1);
    }
}
