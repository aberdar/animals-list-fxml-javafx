package personal;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private final ObservableList<Animal> animalsData = FXCollections.observableArrayList(
            new Animal("Dog", "Mike", 5, 3, "Alexander"),
            new Animal("Cat", "Mars", 5, 7, "Victoria")
    );

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("MainWindow.fxml"));
            Parent root = loader.load();
            MainWindowController controller = loader.getController();
            controller.setData(animalsData);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("mainstyle.css").toExternalForm());
            primaryStage.setTitle("Animals list");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }

}

