package inventory_app.view.tabs.inventory.addView;

import inventory_app.Main;
import inventory_app.model.inventory.Borrowable;
import inventory_app.model.inventory.Borrower;
import inventory_app.model.inventory.Equipment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class addBorrowingViewController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBorrowerByNameFilter();
    }

    @FXML
    private void checkEquipmentReferenceExist(){
        String chosenReference =  equipmentReferenceTextField.getText();
        if(!chosenReference.equalsIgnoreCase("")){
            Optional<Equipment> choosenEquipment =  Main.contextContainer.getInventoryManager().getAll().stream().filter(item -> item.getReference().equalsIgnoreCase(chosenReference)).findAny();
            if(choosenEquipment.isPresent()){
                Equipment equipment = choosenEquipment.get();
                if(Main.contextContainer.getBorrowingsList().isBorrowed(((Borrowable)equipment))){
                    launchAlertBox("Sorry, chosen item is already borrowed",Alert.AlertType.ERROR);
                }
            }else {
                launchAlertBox("Sorry, chosen item does not exist",Alert.AlertType.ERROR);
            }
        }
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


    /* TEMPORARY METHOD ONCE I THINK USING POP-UP IT'S NOT A VERY GOOD PRACTICE */
    private void launchAlertBox(String message, Alert.AlertType alertType){
        Alert isBorrowedAlert = new Alert(alertType);
        isBorrowedAlert.setHeaderText(message);
        isBorrowedAlert.show();
    }
}
