package personal;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.zip.DataFormatException;

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
    public void openAction() {
        try {
            ObservableList<Animal> animalsData = FXCollections.observableArrayList();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            File file = fileChooser.showOpenDialog(null);
            if (file == null) return;

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                try {
                    String[] data = str.split(" +");
                    if(str.isEmpty()) break;
                    if (data.length != 5) throw new DataFormatException("Insufficient data.");

                    String view = data[0];
                    String nickname = data[1];
                    int year = Integer.parseInt(data[2]);
                    int mouth = Integer.parseInt(data[3]);
                    String hostname = data[4];

                    Animal animal = new Animal(view, nickname, year, mouth, hostname);
                    animalsData.add(animal);
                } catch (DataFormatException exception){
                    exception.printStackTrace();
                    bufferedReader.close();
                }
            }
            bufferedReader.close();

            table.setItems(animalsData);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

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
            loader.setLocation(Main.class.getResource("AnimalAddDialog.fxml"));
            Parent root = loader.load();
            AnimalAddDialogController controller = loader.getController();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            Animal animal = new Animal("", "", 0, 0, "");
            stage.setTitle("Add animal");
            stage.setScene(scene);
            controller.setStage(stage);
            controller.setAnimal(animal);
            stage.showAndWait();

            if (controller.getButtonType() == ButtonType.OK) {
                table.getItems().add(animal);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void editAction() {
        try {
            Animal animal = table.getSelectionModel().getSelectedItem();
            if (animal != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("AnimalEditDialog.fxml"));
                Parent root = loader.load();
                AnimalEditDialogController controller = loader.getController();

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Edit animal");
                stage.setScene(scene);
                controller.setAnimal(animal);
                controller.setStage(stage);
                stage.showAndWait();

                if (controller.getButtonType() == ButtonType.OK) {
                    table.refresh();
                }
            } else {
                showSelectedError("No edit items.");
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
            showSelectedError("No deleted item.");
        }
    }

    @FXML
    private void aboutAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About author");
        alert.setContentText("Alexander Berdar");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("alertstyle.css").toExternalForm());
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

    private void showSelectedError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Please select item");
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("alertstyle.css").toExternalForm());
        alert.showAndWait();
    }
}
