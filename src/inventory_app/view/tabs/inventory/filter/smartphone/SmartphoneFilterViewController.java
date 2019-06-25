package inventory_app.view.tabs.inventory.filter.smartphone;

import inventory_app.model.inventory.equipements.Smartphone;
import inventory_app.view.tabs.inventory.InventoryTableController;
import inventory_app.view.tabs.inventory.filter.utils.FilterController;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class SmartphoneFilterViewController implements Initializable,FilterController {

    @FXML
    private ComboBox<String> osFilterCombo;

    private TableView<InventoryTableController.EquipmentRow> equipmentTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void setOsComboFilter() {
        osFilterCombo.getItems().add("ALL");
        for(Smartphone.PHONE_OS os : Smartphone.PHONE_OS.values())
            osFilterCombo.getItems().add(os.toString());
        ObservableList<InventoryTableController.EquipmentRow> allData = equipmentTable.getItems();
        FilteredList<InventoryTableController.EquipmentRow> filteredData = new FilteredList<>(allData, p -> true);
        osFilterCombo.getSelectionModel().selectedItemProperty().addListener( ((observable, oldValue, newValue) -> {
            filteredData.setPredicate(equipmentRow -> {
                if (newValue == "ALL")
                    return true;
                if (((Smartphone)equipmentRow.getEquipment()).getPHONE_OS().toString().equalsIgnoreCase(newValue)) {
                    return true;
                }
                return false;
            });
        }));

        SortedList<InventoryTableController.EquipmentRow> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(equipmentTable.comparatorProperty());
        equipmentTable.setItems(sortedData);
    }


    public void setTableView(TableView<InventoryTableController.EquipmentRow> equipmentTable) {
        this.equipmentTable = equipmentTable;
        setOsComboFilter();
    }
}
