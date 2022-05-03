package personal;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private TableView<Animal> table;
    @FXML
    private TableColumn<Animal, String> viewColumn;
    @FXML
    private TableColumn<Animal, String> nicknameColumn;
    @FXML
    private TableColumn<Animal, Integer> yearsColumn;
    @FXML
    private TableColumn<Animal, Integer> monthsColumn;
    @FXML
    private TableColumn<Animal, String> hostnameColumn;

    @FXML
    private void saveAction() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            File file = fileChooser.showSaveDialog(null);
            if (file == null) return;
            FileWriter out = new FileWriter(file);
            for (Animal animal : table.getItems()) {
                out.write(
                        animal.getView() + " " +
                                animal.getNickname() + " " +
                                animal.getYears() + " " +
                                animal.getMonths() + " " +
                                animal.getHostname() + "\n");
            }
            out.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void exitAction() {
        Platform.exit();
    }

    @FXML
    private void addAction() {
        try {
            FXMLLoader loader = new FXMLLoader();
            AnimalAddDialogController controller = loader.getController();

            loader.setLocation(Main.class.getResource("AnimalAddDialog.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            Animal animal = new Animal("", "", 0, 0, "");
            stage.setTitle("Add animal");
            stage.setScene(scene);
            controller.setStage(stage);
            controller.setAnimal(animal);
            stage.showAndWait();

            if (controller.getResult() == ButtonType.OK) {
                table.getItems().add(animal);
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void deleteAction() {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            table.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select item");
            alert.setContentText("No deleted item.");
            alert.showAndWait();
        }
    }

    @FXML
    private void aboutAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About author");
        alert.setContentText("Alexander Berdar");
        alert.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("view"));
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        yearsColumn.setCellValueFactory(new PropertyValueFactory<>("years"));
        monthsColumn.setCellValueFactory(new PropertyValueFactory<>("months"));
        hostnameColumn.setCellValueFactory(new PropertyValueFactory<>("hostname"));
    }

    public void setData(ObservableList<Animal> animalsData) {
        table.setItems(animalsData);
    }
}
