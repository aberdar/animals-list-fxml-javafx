package personal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AnimalAddDialogController {
    @FXML
    public TextField addView;
    @FXML
    public TextField addNickname;
    @FXML
    public TextField addHostname;
    @FXML
    public TextField addYears;
    @FXML
    public TextField addMonths;

    private Stage stage;
    private Animal animal;
    private ButtonType buttonType = ButtonType.CANCEL;
    private String error = "";

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
        if (addView.getText() == null || addView.getText().length() == 0) {
            error += "No valid view.\n";
        }

        if (addNickname.getText() == null || addNickname.getText().length() == 0) {
            error += "No valid nickname.\n";
        }

        if (addHostname.getText() == null || addHostname.getText().length() == 0) {
            error += "No valid hostname.\n";
        }

        if (addYears.getText() == null || addYears.getText().length() == 0) {
            error += "No valid year.\n";
        } else {
            try {
                Integer.parseInt(addYears.getText());
            } catch (NumberFormatException numberFormatException) {
                error += "No valid year.\n";
            }
        }

        if (addMonths.getText() == null || addMonths.getText().length() == 0) {
            error += "No valid month.\n";
        } else {
            try {
                Integer.parseInt(addMonths.getText());
            } catch (NumberFormatException numberFormatException) {
                error += "No valid month.\n";
            }
        }

        return error.length() == 0;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            animal.setView(addView.getText());
            animal.setNickname(addNickname.getText());
            animal.setHostname(addHostname.getText());
            animal.setYears(Integer.parseInt(addYears.getText()));
            animal.setMonths(Integer.parseInt(addMonths.getText()));

            buttonType = ButtonType.OK;
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(error);
            alert.showAndWait();
            error = "";
        }
    }

    @FXML
    private void handleCancel() {
        buttonType = ButtonType.CANCEL;
        stage.close();
    }
}
