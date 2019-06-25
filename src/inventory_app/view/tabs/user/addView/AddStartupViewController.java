package inventory_app.view.tabs.user.addView;

import inventory_app.Main;
import inventory_app.model.inventory.Incubator;
import inventory_app.model.inventory.Startup;
import inventory_app.view.TextFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class AddStartupViewController implements Initializable {
    @FXML
    private TextField nameField;

    @FXML
    private TextField SIRENField;

    @FXML
    private ComboBox<Incubator> incubatorComboBox;

    @FXML
    private Label newIncubatorLabel;

    @FXML
    private TextField newIncubatorField;

    @FXML
    private Button createStartupButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setNewIncubatorWidgetsVisible(false);
        populateIncubatorComboBox();
        displayNamesInCombo();
    }

    private void setNewIncubatorWidgetsVisible(boolean value) {
        newIncubatorLabel.setVisible(value);
        newIncubatorField.setVisible(value);
    }

    private void displayNamesInCombo() {
        incubatorComboBox.setConverter(new StringConverter<Incubator>() {

            @Override
            public String toString(Incubator incubator) {
                return incubator.getName();
            }

            @Override
            public Incubator fromString(String string) {
                //SHOULD NOT BE REQUIRED
                System.err.println(getClass().getEnclosingMethod().getName() + " is not implemented.");
                return null;
            }
        });
    }

    private void populateIncubatorComboBox() {
        incubatorComboBox.getItems().addAll(
                Main.contextContainer.getUsers().getIncubators()
        );
    }

    @FXML
    private void newIncubatorButtonClicked() {
        newIncubatorField.clear();
        setNewIncubatorWidgetsVisible(!newIncubatorField.isVisible());
    }

    @FXML
    private boolean validateStartupName() {
        return TextFieldValidator.validate(nameField, TextFieldValidator.FieldREGEX.NAME_REGEX);
    }

    @FXML
    private boolean validateSIREN() {
        return TextFieldValidator.validate(SIRENField, TextFieldValidator.FieldREGEX.SIREN);
    }

    @FXML
    private boolean validateIncubatorName() {
        return TextFieldValidator.validate(newIncubatorField, TextFieldValidator.FieldREGEX.NAME_REGEX);
    }

    @FXML
    private void createStartupButtonClicked() {
        if (validateAllFields()) {
            Startup startup;
            String SIRENFieldFormated = SIRENField.getText().replaceAll("\\s+","").trim(); // "123 456 678" -> "123456789"
            if (newIncubatorField.isVisible()) {
                startup = new Startup(nameField.getText().trim(),
                        SIRENFieldFormated,
                        new Incubator(newIncubatorField.getText().trim()));
            } else {
                startup = new Startup(nameField.getText().trim(),
                        SIRENFieldFormated,
                        incubatorComboBox.getValue());
            }
            Main.contextContainer.getUsers().addUser(startup);
            ((Stage)createStartupButton.getScene().getWindow()).close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Add Startup");
            alert.setContentText("Incorrect field(s)");

            // Trick to force the alert to always be displayed on top (sometimes, it popped behind the current window)
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);
            stage.toFront();
            stage.showAndWait();
        }
    }

    private boolean validateAllFields() {
        boolean result = validateStartupName() && validateSIREN();

        if (newIncubatorField.isVisible())
            result = result && validateIncubatorName();

        return result;
    }

}
