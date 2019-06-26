package inventory_app.view.tabs.inventory.addView;

import inventory_app.Main;
import inventory_app.model.inventory.Equipment;
import inventory_app.model.inventory.Institute;
import inventory_app.model.inventory.equipements.Tablet;
import inventory_app.view.TextFieldValidator;
import inventory_app.view.tabs.inventory.InventoryTableController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AddTabletViewController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField brandField;

    @FXML
    private ComboBox<Institute> ownerComboBox;

    @FXML
    private DatePicker purchaseDatePicker;

    @FXML
    private TextField purchasePriceField;

    @FXML
    private ComboBox<Tablet.OS> OSComboBox;

    @FXML
    private TextField resolutionWidthField;

    @FXML
    private TextField resolutionHeightField;

    @FXML
    private ComboBox<Equipment.Condition> conditionComboBox;

    @FXML
    private ComboBox<Equipment.Location> locationComboBox;

    @FXML
    private TextField occurenceField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initDatePicker();
        populateOwnerCombo();
        displayNamesInOwnerCombo();
        populateOSCombo();
        populateConditionCombo();
        populateLocationCombo();

    }

    private void initDatePicker() {
        purchaseDatePicker.setValue(LocalDate.now());
        // Disable dates > today
        purchaseDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) > 0 );
            }
        });
    }

    private void populateLocationCombo() {
        locationComboBox.getItems().setAll(Equipment.Location.values());
        locationComboBox.getSelectionModel().selectFirst();
    }

    private void displayNamesInOwnerCombo() {
        ownerComboBox.setConverter(new StringConverter<Institute>() {
            @Override
            public String toString(Institute object) {
                return object.getName();
            }

            @Override
            public Institute fromString(String string) {
                //NOT NEEDED
                return null;
            }
        });
    }

    @FXML
    private boolean validateOccurence(){
        return TextFieldValidator.validate(occurenceField, TextFieldValidator.FieldREGEX.INTEGER_REGEX) && Integer.parseInt(occurenceField.getText()) > 0;
    }

    private void populateConditionCombo() {
        conditionComboBox.getItems().setAll(Equipment.Condition.values());
        conditionComboBox.setValue(Equipment.Condition.GOOD);
    }

    private void populateOSCombo() {
        OSComboBox.getItems().setAll(Tablet.OS.values());
        OSComboBox.getSelectionModel().selectFirst();
    }

    private void populateOwnerCombo() {
        Set<Institute> institutes = new HashSet<>();

        for (Equipment e : Main.contextContainer.getInventoryManager().getAll()) {
            institutes.add(e.getOwner());
        }
        ownerComboBox.getItems().setAll(institutes);
        ownerComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    private boolean validateName() {
        return TextFieldValidator.validate(nameField, TextFieldValidator.FieldREGEX.NAME_REGEX);
    }

    @FXML
    private boolean validateBrand() {
        return TextFieldValidator.validate(brandField, TextFieldValidator.FieldREGEX.NAME_REGEX);
    }

    @FXML
    private boolean validatePrice() {
        return TextFieldValidator.validate(purchasePriceField, TextFieldValidator.FieldREGEX.NUMBER_REGEX);
    }

    @FXML
    private boolean validateResolution() {
        return TextFieldValidator.validate(resolutionWidthField, TextFieldValidator.FieldREGEX.INTEGER_REGEX)
                &&
                TextFieldValidator.validate(resolutionHeightField, TextFieldValidator.FieldREGEX.INTEGER_REGEX);
    }

    @FXML
    private void confirmButtonClicked() {
        if (validateAllFields() && purchaseDatePicker.getValue() != null) {
            int resolutionWidth = Integer.valueOf(resolutionWidthField.getText().trim());
            int resolutionHeight = Integer.valueOf(resolutionHeightField.getText().trim());
            for(int i = 0 ; i < Integer.parseInt(occurenceField.getText()) ; i++ ){
                Tablet tablet= new Tablet(nameField.getText().trim(),
                        brandField.getText().trim(),
                        ownerComboBox.getValue(),
                        Date.from(purchaseDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        Double.valueOf(purchasePriceField.getText().trim()),
                        conditionComboBox.getValue(),
                        new int[] {resolutionWidth,resolutionHeight},
                        OSComboBox.getValue()
                );
                tablet.setLocation(locationComboBox.getValue());
                InventoryTableController.addEquipment(tablet);
            }
            ((Stage)nameField.getScene().getWindow()).close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Add Smartphone");
            alert.setContentText("Incorrect field(s)");

            // Trick to force the alert to always be displayed on top (sometimes, it popped behind the current window)
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);
            stage.toFront();
            stage.showAndWait();
        }
    }

    private boolean validateAllFields() {

        return validateOccurence() &
                validateName() &
                validateBrand() &
                validatePrice() &
                validateResolution();
    }
}
