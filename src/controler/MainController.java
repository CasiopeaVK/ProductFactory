package controler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import db.DBContext;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupScrollPane.widthProperty().addListener((observableValue, number, t1) -> groupTilePane.setPrefWidth(groupScrollPane.getWidth()));
        ArrayList<ProductGroup> productGroups = new ArrayList<ProductGroup>();
        try {
            dbContext = new DBContext(new File("C:\\Users\\vladk\\IdeaProjects\\ProductFactory\\src\\db\\DB.json"));
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
    }

    private void test() throws IOException {
        File file = new File("C:\\Users\\vladk\\IdeaProjects\\ExcelTest\\src\\grecha.jpg");
        Image image = new Image("grecha.jpg");
        ArrayList<ProductGroup> groups = new ArrayList<ProductGroup>();
        for (int i = 0; i < 20; i++) {
            ProductGroup productGroup = new ProductGroup(image, "EKE" + i, "norm");
            for (int j = 0; j < 20; j++) {
                Product product = new Product("Name - " + j, 10, 69.9, true);
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
    }

    @FXML
    public void addNewGroup(ActionEvent actionEvent) {
        AddNewGroupController groupController = new AddNewGroupController(groupTilePane);
        Scene scene = new Scene(groupController, 400, 300);
        Stage window = new Stage();
        window.setTitle("Add new group:");
        window.setScene(scene);
        window.show();
    }
}
