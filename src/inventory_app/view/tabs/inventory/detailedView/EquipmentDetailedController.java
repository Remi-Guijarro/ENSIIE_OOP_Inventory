package inventory_app.view.tabs.inventory.detailedView;

import inventory_app.model.inventory.Equipment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class EquipmentDetailedController implements Initializable {
    private Stage detailedView;

    @FXML
    private AnchorPane root;

    @FXML
    private Label typeLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label idLabel;


    @FXML
    private TextField nameField;


    @FXML
    private TextField brandField;


    @FXML
    private TextField purchasePrice;

    @FXML
    private DatePicker purchaseDatePicker;

    @FXML
    private ComboBox<Equipment.Condition> conditionComboBox;

    @FXML
    private ComboBox<Equipment.Location> locationComboBox;

    private Equipment equipment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        detailedView = new Stage();
        detailedView.initModality(Modality.APPLICATION_MODAL);
        detailedView.initStyle(StageStyle.DECORATED);
        detailedView.setTitle("Details");
        Scene scene = new Scene(root);
        detailedView.setScene(scene);
        detailedView.setAlwaysOnTop(true);
        detailedView.setResizable(true);
        detailedView.show();
    }

    public void setDetailedInfo() {
        if (equipment != null) {
            idLabel.setText(equipment.getReference());
            nameField.setText(equipment.getName());
            brandField.setText(equipment.getBrand());
            purchaseDatePicker.setValue(equipment.getPurchaseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            purchasePrice.setText(String.valueOf(equipment.getPurchasePrice()));
            conditionComboBox.setValue(equipment.getCondition());
            locationComboBox.setValue(equipment.getLocation());
        }
    }

    public void setTypeLabel(String text) {
        typeLabel.setText(text);
    }

    public void addTextField(Label name, TextField field) {
        int rowIndex = gridPaneRowCount();
        name.setTextFill(Color.WHITE);
        gridPane.add(name, 0, rowIndex);
        gridPane.add(field, 1, rowIndex);
    }

    private int gridPaneRowCount() {
        int numRows = gridPane.getRowConstraints().size();
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            Node child = gridPane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null){
                    numRows = Math.max(numRows,rowIndex+1);
                }
            }
        }
        return numRows;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Equipment getEquipment() {
        return equipment;
    }
}
