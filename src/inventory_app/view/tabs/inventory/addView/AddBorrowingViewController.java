package inventory_app.view.tabs.inventory.addView;

import inventory_app.Main;
import inventory_app.model.inventory.Borrowable;
import inventory_app.model.inventory.Borrower;
import inventory_app.model.inventory.Equipment;
import inventory_app.view.tabs.inventory.InventoryTableController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddBorrowingViewController implements Initializable {

    private Stage stage;
    @FXML
    private AnchorPane root;

    @FXML
    private TextField equipmentReferenceTextField;

    @FXML
    private Button checkRefenceButton;

    @FXML
    private TextField borrowerNameTextField;

    @FXML
    private ComboBox<Borrower> borrowerChooserComboBox;

    @FXML
    private DatePicker retunDatePicker;

    @FXML
    private Button validateFormButton;

    @FXML
    private TableView<InventoryTableController.EquipmentRow> tableView;

    private Equipment desiredEquipement;
    private Borrower desiredBorrower;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBorrowerByNameFilter();
        setBorrowerChooserComboBoxDefaultValue();
    }

    public void setTableView(TableView<InventoryTableController.EquipmentRow> tableView) {
        this.tableView = tableView;
    }

    @FXML
    private void checkEquipmentReferenceExist(){
        String chosenReference =  equipmentReferenceTextField.getText();
        Optional<Equipment> choosenEquipment =  Main.contextContainer.getInventoryManager().getAll().stream().filter(item -> item.getReference().equalsIgnoreCase(chosenReference)).findAny();
        if(choosenEquipment.isPresent()){
            Equipment equipment = choosenEquipment.get();
            if(Main.contextContainer.getBorrowingsList().isBorrowed(((Borrowable)equipment))){
                launchAlertBox("Sorry, chosen item is already borrowed",Alert.AlertType.ERROR);
            } else {
                desiredEquipement = equipment;
            }
        }else {
            launchAlertBox("Sorry, chosen item does not exist",Alert.AlertType.ERROR);
        }
    }

    private void setBorrowerChooserComboBoxDefaultValue(){
        if(borrowerChooserComboBox.getItems().size() > 0)
            borrowerChooserComboBox.getItems().removeAll(borrowerChooserComboBox.getItems());
        borrowerChooserComboBox.getItems().addAll(Main.contextContainer.getUsers()
                .get()
                .stream()
                .collect(Collectors.toList())
        );
        displayNamesOnBorrowerChooserCombo();
    }

    private void setBorrowerByNameFilter(){
        borrowerNameTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(borrowerChooserComboBox.getItems().size() > 0)
                borrowerChooserComboBox.getItems().removeAll(borrowerChooserComboBox.getItems());
            borrowerChooserComboBox.getItems().addAll(Main.contextContainer.getUsers()
                    .get()
                    .stream()
                    .filter(item -> item.getName().contains(newValue))
                    .collect(Collectors.toList())
            );
            displayNamesOnBorrowerChooserCombo();
        }));
    }

    private void displayNamesOnBorrowerChooserCombo() {
        borrowerChooserComboBox.setConverter(new StringConverter<Borrower>() {
            @Override
            public String toString(Borrower borrower) {
                return borrower.getName();
            }

            @Override
            public Borrower fromString(String string) {
                return borrowerChooserComboBox.getItems().stream().filter(borrower ->
                        borrower.getName().equalsIgnoreCase(string)).findAny().orElse(null);
            }
        });
    }

    public void openView(){
        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }



    private boolean checkSelectedEquipmentFields(){
        if(desiredEquipement != null)
            return true;
        return false;
    }

    private boolean checkSelectedBorrowerFields(){
        if(borrowerChooserComboBox.getSelectionModel().getSelectedItem() != null){
            desiredBorrower = borrowerChooserComboBox.getSelectionModel().getSelectedItem();
            return true;
        }
        return false;
    }

    private boolean checkSelectedReturnDateFields(){
        if(retunDatePicker.getValue().isAfter(LocalDate.now()))
            return true;
        return false;
    }

    private boolean checkFields(){
        return checkSelectedBorrowerFields() &&
                checkSelectedEquipmentFields() &&
                checkSelectedReturnDateFields();
    }

    @FXML
    private void validateForm(){
        if(tableView != null){
            if(checkFields()){
                Date returnDate = Date.from(Instant.from(retunDatePicker.getValue().atStartOfDay(ZoneId.systemDefault())));
                Main.contextContainer.getBorrowingsList().addBorrowedItem(desiredEquipement,returnDate,"My Custom reason",desiredBorrower);
                InventoryTableController.EquipmentRow row = tableView.getItems().stream().filter(item -> item.getId().equalsIgnoreCase(desiredEquipement.getReference())).findAny().orElse(null);
                row = new InventoryTableController.EquipmentRow(row.getId(),row.getType(),row.getName(),row.getBrand(),row.getOwner(),row.getCondition(),desiredBorrower.getName(),
                        "My Custom reason",returnDate.toString(),row.getEquipment());
                tableView.refresh();
                stage.close();
            }
        }
    }

    /* TEMPORARY METHOD ONCE I THINK USING POP-UP IT'S NOT A VERY GOOD PRACTICE */
    private void launchAlertBox(String message, Alert.AlertType alertType){
        Alert isBorrowedAlert = new Alert(alertType);
        isBorrowedAlert.setHeaderText(message);
        isBorrowedAlert.show();
    }
}
