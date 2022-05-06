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

public class AnimalEditDialogController implements Initializable {
    @FXML
    public ChoiceBox<String> editView;
    @FXML
    public TextField editNickname;
    @FXML
    public TextField editHostname;
    @FXML
    public Spinner<Integer> editYears;
    @FXML
    public Spinner<Integer> editMonths;

    private Animal animal;
    private Stage stage;
    private ButtonType buttonType = ButtonType.CANCEL;
    private String error = "";
    private final String[] animals = {"Dog", "Cat", "Turtle", "Rabbit", "Parrot", "Snake", "Mink"};

    public void setAnimal(Animal animal) {
        this.animal = animal;

        editView.setValue(this.animal.getView());
        editNickname.setText(this.animal.getNickname());
        editHostname.setText(this.animal.getHostname());
        editYears.getValueFactory().setValue(this.animal.getYears());
        editMonths.getValueFactory().setValue(this.animal.getMonths());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ButtonType getButtonType() {
        return buttonType;
    }

    private boolean isInputValid() {
        if (editNickname.getText() == null ||
                editNickname.getText().length() == 0 ||
                !Character.isUpperCase(editNickname.getText().charAt(0)) ||
                !editNickname.getText().matches("[a-zA-Z]+")
        ) {
            error += "No valid nickname.\n";
        }

        if (editHostname.getText() == null ||
                editHostname.getText().length() == 0 ||
                !Character.isUpperCase(editHostname.getText().charAt(0)) ||
                !editHostname.getText().matches("[a-zA-Z]+")
        ) {
            error += "No valid hostname.\n";
        }

        if (editYears.getValue() == 0) {
            error += "No valid year.\n";
        }

        if (editMonths.getValue() == 0) {
            error += "No valid month.\n";
        }

        return error.length() == 0;
    }

    @FXML
    public void handleOk() {
        if (isInputValid()) {
            animal.setView(editView.getSelectionModel().getSelectedItem());
            animal.setNickname(editNickname.getText());
            animal.setHostname(editHostname.getText());
            animal.setYears(editYears.getValue());
            animal.setMonths(editMonths.getValue());

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
    public void handleCancel() {
        buttonType = ButtonType.CANCEL;
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editView.getItems().addAll(animals);
    }
}
