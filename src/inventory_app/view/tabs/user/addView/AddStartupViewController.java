package inventory_app.view.tabs.user.addView;

import inventory_app.Main;
import inventory_app.model.inventory.Incubator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private Button newIncubatorButton;

    @FXML
    private Label newIncubatorLabel;

    @FXML
    private TextField newIncubatorField;

    @FXML
    private Button newIncubationConfirmButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setNewIncubatorWidgetsVisible(false);
        populateIncubatorComboBox();
        displayNamesInCombo();
    }

    private void setNewIncubatorWidgetsVisible(boolean value) {
        newIncubatorLabel.setVisible(value);
        newIncubatorField.setVisible(value);
        newIncubationConfirmButton.setVisible(value);
    }

    private void displayNamesInCombo() {
        incubatorComboBox.setConverter(new StringConverter<Incubator>() {

            @Override
            public String toString(Incubator incubator) {
                return incubator.getName();
            }

            @Override
            public Incubator fromString(String string) {
                //NOT NEEDED
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
        setNewIncubatorWidgetsVisible(!newIncubationConfirmButton.isVisible());
    }

}
