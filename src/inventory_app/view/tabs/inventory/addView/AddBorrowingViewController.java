package inventory_app.view.tabs.inventory.addView;

import inventory_app.Main;
import inventory_app.model.inventory.Borrowable;
import inventory_app.model.inventory.Borrower;
import inventory_app.model.inventory.Equipment;
import inventory_app.view.tabs.inventory.InventoryTableController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class AddBorrowingViewController implements Initializable {

    private enum LevelColor {

        ERROR(Level.SEVERE,Color.RED),
        INFO(Level.INFO,Color.GREEN);

        private Level level;
        private Color color;

        LevelColor(Level level,Color color){
            this.level = level;
            this.color = color;
        }

        public Level getLevel() {
            return level;
        }

        public Color getColor() {
            return color;
        }
    }

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
    private HBox logMessageBox;

    private InventoryTableController tableController;

    private Equipment desiredEquipement;
    private Borrower desiredBorrower;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBorrowerByNameFilter();
        setBorrowerChooserComboBoxDefaultValue();
    }

    public void setDefaultReferenceValue(String id) {
        equipmentReferenceTextField.setText(id);
        checkEquipmentReferenceExist(false);
    }

    public void setTableController(InventoryTableController tableController){
        this.tableController = tableController;
    }

    private void addLogMessage(String message, Level level){
        Timer timer = new Timer();
        Label logLabel = new Label(message);
        for(LevelColor lv : LevelColor.values()){
            if(lv.getLevel().equals(level)){
                logLabel.setBackground(new Background(new BackgroundFill(lv.getColor(),CornerRadii.EMPTY,Insets.EMPTY)));
                logLabel.setPrefWidth(logMessageBox.getWidth());
                logLabel.setPrefHeight(Double.valueOf(20));
                logLabel.setFont(new Font("System",14.0));
                logLabel.setAlignment(Pos.CENTER);
                logLabel.setTextFill(Color.WHITE);
            }
        }
        if(logMessageBox.getChildren().size() > 0)
            logMessageBox.getChildren().remove(0);
        logMessageBox.getChildren().add(logLabel);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(logMessageBox.getChildren().size() > 0)
                        logMessageBox.getChildren().remove(0);
                });
            }
        },3000);
    }

    @FXML
    private void checkEquipmentReference(){
        checkEquipmentReferenceExist(true);
    }


    private void checkEquipmentReferenceExist(boolean displayMessage){
        String chosenReference =  equipmentReferenceTextField.getText();
        Optional<Equipment> choosenEquipment =  Main.contextContainer.getInventoryManager().getAll().stream().filter(item -> item.getReference().equalsIgnoreCase(chosenReference)).findAny();
        if(choosenEquipment.isPresent()){
            Equipment equipment = choosenEquipment.get();
            if(Main.contextContainer.getBorrowingsList().isBorrowed(((Borrowable)equipment))){
                if(displayMessage)
                    addLogMessage("Sorry, chosen item is already borrowed",Level.SEVERE);
            } else {
                if(displayMessage)
                    addLogMessage("OK",Level.INFO);
                desiredEquipement = equipment;
            }
        }else {
            if(displayMessage)
                addLogMessage("Sorry, chosen item does not exist",Level.SEVERE);
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
        addLogMessage("Equipment must be selected ",Level.SEVERE);
        return false;
    }

    private boolean checkSelectedBorrowerFields(){
        if(borrowerChooserComboBox.getSelectionModel().getSelectedItem() != null){
            desiredBorrower = borrowerChooserComboBox.getSelectionModel().getSelectedItem();
            return true;
        }
        addLogMessage("Borrower must be selected ",Level.SEVERE);
        return false;
    }

    private boolean checkSelectedReturnDateFields(){
        if(retunDatePicker.getValue() != null){
            if(retunDatePicker.getValue().isAfter(LocalDate.now()))
                return true;
            addLogMessage("Return date must be at least tomorrow ",Level.SEVERE);
            return false;
        }else {
            addLogMessage("Return date must be selected ",Level.SEVERE);
            return false;
        }
    }

    private boolean checkFields(){
        return checkSelectedBorrowerFields() &&
                checkSelectedEquipmentFields() &&
                checkSelectedReturnDateFields();
    }

    @FXML
    private void validateForm(){
        if(tableController != null && tableController.getTableView() != null){
            if(checkFields()){
                Date returnDate = Date.from(Instant.from(retunDatePicker.getValue().atStartOfDay(ZoneId.systemDefault())));
                Main.contextContainer.getBorrowingsList().addBorrowedItem(desiredEquipement,returnDate,"My Custom reason",desiredBorrower);
                tableController.populateTableBy(Equipment.class);
                stage.close();
            }
        }
    }

    /* TEMPORARY METHOD ONCE I THINK USING POP-UP IT'S NOT A VERY GOOD PRACTICE */
   /* private void launchAlertBox(String message, Alert.AlertType alertType){
        Alert isBorrowedAlert = new Alert(alertType);
        isBorrowedAlert.setHeaderText(message);
        isBorrowedAlert.show();
    } */
}
