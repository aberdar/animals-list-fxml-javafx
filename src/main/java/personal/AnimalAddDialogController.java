package personal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AnimalAddDialogController implements Initializable {
    @FXML
    public ChoiceBox<String> addView;
    @FXML
    public TextField addNickname;
    @FXML
    public TextField addHostname;
    @FXML
    public Spinner<Integer> addYears;
    @FXML
    public Spinner<Integer> addMonths;

    private Stage stage;
    private Animal animal;
    private ButtonType buttonType = ButtonType.CANCEL;
    private String error = "";
    private final String[] animals = {"Dog", "Cat", "Turtle", "Rabbit", "Parrot", "Snake", "Mink"};

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public ButtonType getButtonType() {
        return buttonType;
    }

    private boolean isInputValid() {
        if (addNickname.getText() == null ||
            addNickname.getText().length() == 0 ||
            !Character.isUpperCase(addNickname.getText().charAt(0)) ||
            !addNickname.getText().matches("[a-zA-Z]+")
        ) {
            error += "No valid nickname.\n";
        }

        if (addHostname.getText() == null ||
            addHostname.getText().length() == 0 ||
            !Character.isUpperCase(addHostname.getText().charAt(0)) ||
            !addHostname.getText().matches("[a-zA-Z]+")
        ) {
            error += "No valid hostname.\n";
        }

        if (addYears.getValue() == 0) {
            error += "No valid year.\n";
        }

        if (addMonths.getValue() == 0) {
            error += "No valid month.\n";
        }

        return error.length() == 0;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            animal.setView(addView.getSelectionModel().getSelectedItem());
            animal.setNickname(addNickname.getText());
            animal.setHostname(addHostname.getText());
            animal.setYears(addYears.getValue());
            animal.setMonths(addMonths.getValue());

            buttonType = ButtonType.OK;
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(error);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("alertstyle.css").toExternalForm());
            alert.showAndWait();
            error = "";
        }
    }

    @FXML
    private void handleCancel() {
        buttonType = ButtonType.CANCEL;
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addView.getItems().addAll(animals);
        addView.setValue(animals[0]);
    }
}
