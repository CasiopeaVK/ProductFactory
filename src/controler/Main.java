package controler;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/window_main.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 700));
        primaryStage.show();
        ((MainController) fxmlLoader.getController()).setStage(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
