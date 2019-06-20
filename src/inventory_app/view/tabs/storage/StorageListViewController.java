package inventory_app.view.tabs.storage;

import inventory_app.Main;
import inventory_app.model.inventory.Borrower;
import inventory_app.model.inventory.Equipment;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class StorageListViewController implements Initializable {

    private enum subSetType { TOTAL, AVAILABLE, BORROWED };

    @FXML
    private ComboBox<String> subSetCombo;

    @FXML
    private ListView<Equipment> equipmentList;

    @FXML
    private TextField searchField;

    public ListView getEquipmentList(){
        return equipmentList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTable();
        setListenerOnTableItems();
        setSearchFilter();
        setSubSetFilter();
    }

    private void setListenerOnTableItems() {
        /*
        equipmentList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue instanceof Startup){
                        FXMLLoader loader = null;
                        try {
                            loader = loadDetailedView("detailedView/companyListViewDetailed.fxml");
                            ((CompanyListViewDetailedController)loader.getController()).setDetailedInfo((Borrower) newValue);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (newValue instanceof People){
                        FXMLLoader loader = null;
                        try {
                            loader = loadDetailedView("detailedView/peopleListViewDetailed.fxml");
                            ((PeopleListViewDetailedController)loader.getController()).setDetailedInfo((Borrower) newValue, equipmentList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        */
    }

    private void  populateTable() {
        equipmentList.getItems().addAll(Main.contextContainer.getInventoryManager().getAll());
        equipmentList.setCellFactory(param -> new ListCell<Equipment>() {
            @Override
            protected void updateItem(Equipment equipment, boolean empty) {
                super.updateItem(equipment, empty);

                if (empty || equipment == null || equipment.getName() == null) {
                    setText(null);
                } else {
                    setText(equipment.getName());
                }
            }
        });
    }

    private void setSearchFilter() {
        ObservableList<Equipment> allData = equipmentList.getItems();
        FilteredList<Equipment> filteredData = new FilteredList<>(allData, p -> true);
        searchField.textProperty().addListener( (observable, oldValue, newValue) -> {
            filteredData.setPredicate( equipment -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (equipment.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Equipment> sortedData = new SortedList<>(filteredData);
        equipmentList.setItems(sortedData);
    }


    private void setSubSetFilter(){
        for(subSetType s : subSetType.values()){
            subSetCombo.getItems().add(s.toString());
        }
        subSetCombo.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
            if(newValue.equalsIgnoreCase(subSetType.AVAILABLE.toString())){
                equipmentList.getItems().removeAll(equipmentList.getItems());
                equipmentList.getItems().addAll(Main.contextContainer.getInventoryManager().getAvailable());
            } else if(newValue.equalsIgnoreCase(subSetType.BORROWED.toString())){
                equipmentList.getItems().removeAll(equipmentList.getItems());
                equipmentList.getItems().addAll(Main.contextContainer.getInventoryManager().getBorrowed());
            } else if(newValue.equalsIgnoreCase(subSetType.TOTAL.toString())){
                equipmentList.getItems().removeAll(equipmentList.getItems());
                equipmentList.getItems().addAll(Main.contextContainer.getInventoryManager().getAll());
            }
        });
    }
}
