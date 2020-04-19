package controler;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.*;
import db.DBContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Product;
import model.ProductGroup;
import model.WriteOffProduct;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Tab statisticWindow;

    @FXML
    private Tab writeWindow;

    @FXML
    private Label countOfGroupLabel;

    @FXML
    private Label countOfProductsLabel;

    @FXML
    private Label totalCostLabel;

    @FXML
    private VBox vBoxAdaptive;

    @FXML
    private TilePane diagramTilePane;
    @FXML
    private JFXComboBox<ProductGroup> chooseProductGroup;

    @FXML
    private JFXComboBox<Product> chooseProduct;

    @FXML
    private VBox checkBoxPlate;

    @FXML
    private JFXSlider chooseSlider;

    @FXML
    private JFXButton btn_addGroup;

    @FXML
    private JFXButton btn_createDiagram;

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
    private GridPane tableCell;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private PieChart diagramCircle;
    ArrayList<PieChart.Data> dataCircle = new ArrayList<PieChart.Data>();
    @FXML
    private BarChart<String, Number> diagramGistogram;
    XYChart.Series<String, Number> dataSeries;
    ArrayList<XYChart.Series<String, Number>> data = new ArrayList<XYChart.Series<String, Number>>();

    private WriteOffTable writeOffTable;

    private DBContext dbContext;
    private ArrayList<CardController> cards = new ArrayList<CardController>();
    private ArrayList<JFXCheckBox> checkBoxes = new ArrayList<JFXCheckBox>();
<<<<<<< HEAD
    public ArrayList<WriteOffProduct> writeOffProductArrayList = new ArrayList<WriteOffProduct>();
=======
    private ArrayList<Product> products = new ArrayList<Product>();

>>>>>>> 3b4db2b1a5d1f7d8e102a9c05ec9f07faf7358a7
    private ObservableList<ProductGroup> listGroup = FXCollections.observableArrayList();
    private ObservableList<Product> listProduct = FXCollections.observableArrayList();
    private ObservableList<WriteOffTable> listWriteOffTable = FXCollections.observableArrayList();

    private ProductGroup currentGroup;
    private Product currentProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupScrollPane.widthProperty().addListener((observableValue, number, t1) -> groupTilePane.setPrefWidth(groupScrollPane.getWidth()));
        vBoxAdaptive.widthProperty().addListener((observableValue, number, t1) -> diagramTilePane.setPrefWidth(vBoxAdaptive.getWidth()));
        initSlider();

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


        initCheckBoxPlate();
        initWriteOffTable();
    }

    private void initWriteOffTable() {
        WriteOffTable writeOffTable = new WriteOffTable(writeOffProductArrayList);
        this.writeOffTable = writeOffTable;
        tableCell.getChildren().add(writeOffTable);
    }

    private void test() throws IOException {
        File file = new File("C:\\Users\\vladk\\IdeaProjects\\ExcelTest\\src\\grecha.jpg");
        Image image = new Image("grecha.jpg");
        ArrayList<ProductGroup> groups = new ArrayList<ProductGroup>();
        for (int i = 0; i < 20; i++) {
            ProductGroup productGroup = new ProductGroup(image, "EKE" + i, "norm");
            for (int j = 0; j < 20; j++) {
                String name = "Name - " + j;
                double price = 1 + j * 4;
                int quantity = 1 + j * 2;

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
        CardController cardController = new CardController(group, cards, products);
        groupTilePane.getChildren().add(cardController);
        cards.add(cardController);
        products.addAll(cardController.getGroup().getProduct());
    }

<<<<<<< HEAD
    @FXML
    void writeOffProducts(ActionEvent event) {
        System.out.println("KEK");
        Product curProduct = chooseProduct.getValue();
        Double writeOffQuantity = curProduct.getQuantity() - chooseSlider.getValue();
        curProduct.setQuantity((int) Math.round(writeOffQuantity));
        Integer quantity = curProduct.getQuantity();
        writeOffProductArrayList.add(new WriteOffProduct(curProduct.getName(), quantity, curProduct.getPrice() * quantity));
    }

    @FXML
    public void addComboBoxGroup() {

        listGroup = FXCollections.observableArrayList();
        for (CardController card : cards)
            listGroup.add(card.getGroup());
        chooseProductGroup.setItems(listGroup);
    }

    @FXML
    public void addNewGroup(ActionEvent actionEvent) {
        AddNewGroupController groupController = new AddNewGroupController(groupTilePane, cards);
=======

    @FXML
    public void addNewGroup(ActionEvent actionEvent) {
        AddNewGroupController groupController = new AddNewGroupController(groupTilePane,cards,products);
>>>>>>> 3b4db2b1a5d1f7d8e102a9c05ec9f07faf7358a7
        Scene scene = new Scene(groupController, 400, 300);
        Stage window = new Stage();
        window.setTitle("Add new group:");
        window.setScene(scene);
        window.show();

    }

    @FXML
    private void initCheckBoxPlate() {
        setStatistic();

        for (JFXCheckBox box : checkBoxes) {
            checkBoxPlate.getChildren().remove(box);
        }

        checkBoxes = new ArrayList<JFXCheckBox>();
        for (CardController card : cards) {
            JFXCheckBox box = new JFXCheckBox();
            box.setText(card.getName());
            checkBoxes.add(box);
            checkBoxPlate.getChildren().add(box);
        }
    }

    private void setStatistic() {
        countOfGroupLabel.setText("" + cards.size());

        int cost = 0;
        int countProducts = 0;
        for (CardController card : cards) {
            countProducts += card.getCountProducts();
            cost += card.getPrice();
        }
        totalCostLabel.setText(cost + "");
        countOfProductsLabel.setText(countProducts + "");


    }

    private void addCardsGroupToCanvas() {
        for (CardController card : cards)
            groupTilePane.getChildren().add(card);
    }

    private void removeAllCardsFromCanvas() {
        for (CardController card : cards) {
            groupTilePane.getChildren().remove(card);
        }
    }

    @FXML
    public void sortCardByAlphabet() {
        removeAllCardsFromCanvas();

        cards.sort((x, y) -> {
            String xs = x.getName();
            String ys = y.getName();
            return xs.compareTo(ys);
        });

        addCardsGroupToCanvas();
    }

    @FXML
    public void sortCardByCost() {
        removeAllCardsFromCanvas();

        cards.sort((x, y) -> (int) (y.getPrice() - x.getPrice()));

        addCardsGroupToCanvas();
    }

    @FXML
    public void sortByCountProducts() {
        removeAllCardsFromCanvas();
        cards.sort((x, y) -> y.getCountProducts() - x.getCountProducts());
        addCardsGroupToCanvas();
    }

    @FXML
<<<<<<< HEAD
    public void chooseProductGroupListener() {
=======
    public void chooseProductGroupListener(){
        if(chooseProductGroup.getValue() == null)
            return;

>>>>>>> 3b4db2b1a5d1f7d8e102a9c05ec9f07faf7358a7
        currentGroup = chooseProductGroup.getValue();
        ArrayList<Product> products = currentGroup.getProduct();
        listProduct = FXCollections.observableArrayList();
        for (Product product : products) {
            listProduct.add(product);
        }
        chooseProduct.getItems().clear();
        chooseProduct.setItems(listProduct);
    }

    @FXML
<<<<<<< HEAD
    public void chooseProductListener() {
=======
    public void addComboBoxGroup(){
        if(!writeWindow.isSelected())
            return;

        chooseProductGroup.getItems().clear();
        chooseProduct.getItems().clear();
        listGroup = FXCollections.observableArrayList();
        for(CardController card : cards)
            listGroup.add(card.getGroup());
        chooseProductGroup.setItems(listGroup);
    }

    @FXML
    public void chooseProductListener(){
        if(chooseProduct.getValue() == null){
            initSlider();
            return;
        }

>>>>>>> 3b4db2b1a5d1f7d8e102a9c05ec9f07faf7358a7
        currentProduct = chooseProduct.getValue();
        chooseSlider.setMin(1);
        chooseSlider.setMax(currentProduct.getQuantity());
        chooseSlider.setValue(1);
    }

    private void initSlider(){
        chooseSlider.setMin(0);
        chooseSlider.setMax(0);
        chooseSlider.setValue(0);
    }
    @FXML
<<<<<<< HEAD
    public void createDiagram() {
=======
    public void createDiagram(){
        if(!statisticWindow.isSelected())
            return;

>>>>>>> 3b4db2b1a5d1f7d8e102a9c05ec9f07faf7358a7
        ArrayList<CardController> show = new ArrayList<CardController>();

        for (JFXCheckBox box : checkBoxes) {
            if (!box.isSelected())
                continue;

            String boxName = box.getText();
            for (CardController card : cards) {
                if (boxName.equals(card.getName()))
                    show.add(card);
            }
        }

        createGistogram(show);
        createDiagramCircle(show);
    }

    private void createDiagramCircle(ArrayList<CardController> show) {
        for (PieChart.Data data : dataCircle)
            diagramCircle.getData().remove(data);

        dataCircle = new ArrayList<PieChart.Data>();

        for (CardController card : show) {
            PieChart.Data slice = new PieChart.Data(card.getName(), card.getCountProducts());
            dataCircle.add(slice);
            diagramCircle.getData().add(slice);
        }
    }

    private void createGistogram(ArrayList<CardController> show) {

        for (XYChart.Series<String, Number> dat : data)
            diagramGistogram.getData().remove(dat);

        data = new ArrayList<XYChart.Series<String, Number>>();

        for (CardController card : show) {
            dataSeries = new XYChart.Series<String, Number>();
            dataSeries.setName(card.getName());
            dataSeries.getData().add(new XYChart.Data<String, Number>("", card.getPrice()));
            data.add(dataSeries);
            diagramGistogram.getData().add(dataSeries);
        }
    }


}
