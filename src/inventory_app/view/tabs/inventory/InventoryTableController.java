package inventory_app.view.tabs.inventory;

import inventory_app.Main;
import inventory_app.model.inventory.Borrowable;
import inventory_app.model.inventory.Borrower;
import inventory_app.model.inventory.Borrowing;
import inventory_app.model.inventory.Equipment;
import inventory_app.view.tabs.inventory.addView.AddBorrowingViewController;
import inventory_app.view.tabs.inventory.filter.utils.FilterControllerLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.omg.CORBA.MARSHAL;
import org.reflections.Reflections;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class InventoryTableController implements Initializable {

    private Reflections reflections;

    @FXML
    private TableView<EquipmentRow> equipmentTable;

    @FXML
    private TableColumn<String,EquipmentRow> idItemColumn;

    @FXML
    private TableColumn<String,EquipmentRow> itemTypeColumn;

    @FXML
    private TableColumn<String,EquipmentRow> itemNameColumn;

    @FXML
    private TableColumn<String,EquipmentRow> itemBrandColumn;

    @FXML
    private TableColumn<String,EquipmentRow> itemOwnerColumn;

    @FXML
    private TableColumn<String,EquipmentRow> itemConditionColumn;

    @FXML
    private TableColumn<String,EquipmentRow>  itemBorrowerColumn;

    @FXML
    private TableColumn<String,EquipmentRow> borrowReasonColumn;

    @FXML
    private TableColumn<String,EquipmentRow> returnDateColumn;

    @FXML
    private ComboBox<Class> typeFilterCombo;

    @FXML
    private Label totalCountLabel;

    @FXML
    private VBox specificFilterVBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setColumnProperty();
        populateTableBy(Equipment.class);
        setTypeFilter();
        System.out.println("PackageName " +Equipment.class.getPackage().getName());
    }

    private void setTypeFilter() {
        populateTypeCombo();
        typeFilterCombo.getSelectionModel().selectedItemProperty().addListener((opt,oldType,newType) -> {
            if(specificFilterVBox.getChildren().size() > 0)
                specificFilterVBox.getChildren().remove(0);
            populateTableBy(newType);
            if(!Equipment.class.equals(newType)){
                FilterControllerLoader filterControllerLoader = new FilterControllerLoader();
                FXMLLoader fxmlLoader =  filterControllerLoader.loadFrom(newType);
                Parent parent = null;
                try {
                    parent = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                    // Logger will log that error append during loading of the filter view or something
                }
                specificFilterVBox.getChildren().add(parent);
            }
        });
        updateTableViewCount();
    }

    private void populateTypeCombo() {
        reflections = new Reflections(Equipment.class.getPackage().getName());
        Set<Class<? extends  Equipment>> classSet = reflections.getSubTypesOf(Equipment.class);
        typeFilterCombo.getItems().add(Equipment.class);
        typeFilterCombo.getItems().addAll(classSet);
        displayNamesInTypeFilterCombo();
    }

    private void updateTableViewCount(){
        totalCountLabel.setText("Total Count : " + equipmentTable.getItems().size());
    }

    private void displayNamesInTypeFilterCombo() {
        typeFilterCombo.setConverter(new StringConverter<Class>() {
            @Override
            public String toString(Class object) {
                if(object.getSimpleName().equalsIgnoreCase(Equipment.class.getSimpleName())){
                    return "All";
                }else{
                    return object.getSimpleName();
                }
            }

            @Override
            public Class fromString(String string) {
                // NOT USED
                return null;
            }
        });
    }

    @FXML
    private void openAddBorrowView() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("addView/addBorrowingView.fxml"));
        Parent node = loader.load();
        AddBorrowingViewController controller = loader.getController();
        controller.setTableView(equipmentTable);
        Stage stage = new Stage();
        Scene scene = new Scene(node);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    private void setColumnProperty(){
        idItemColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemBrandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        itemOwnerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
        itemConditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
        itemBorrowerColumn.setCellValueFactory(new PropertyValueFactory<>("borrower"));
        borrowReasonColumn.setCellValueFactory(new PropertyValueFactory<>("borrowReason"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }

    /**
     * This method populate the table by the given Class
     * @param type -> the wanted Class
     */
    private void populateTableBy(Class type){
        if(!equipmentTable.getItems().isEmpty())
            equipmentTable.getItems().removeAll(equipmentTable.getItems());
        Main.contextContainer.getInventoryManager().getAll().stream().filter(item -> type.isInstance(item)).forEach(equipment -> {
            Borrowing borrowing =  Main.contextContainer.getBorrowingsList().getBorrowerFrom(((Borrowable)equipment));
            String borrowReason = "N/A";
            String borrowerName = "N/A";
            String returnDate = "N/A";
            if(null != borrowing){
                borrowReason = borrowing.getBorrowReason();
                borrowerName = borrowing.getBorrower().getName();
                returnDate = borrowing.getReturnDate().toString();
            }
            equipmentTable.getItems().add(new EquipmentRow(equipment.getReference(),
                    equipment.getClass().getSimpleName(),
                    equipment.getName(),
                    equipment.getBrand(),
                    equipment.getOwner().getName(),
                    equipment.getCondition().toString(),
                    borrowerName,
                    borrowReason,
                    returnDate,
                    equipment)
            );
        });
        updateTableViewCount();
    }


    public class EquipmentRow {
        private String id;
        private String type;
        private String name;
        private String brand;
        private String owner;
        private String condition;
        private String borrower;
        private String borrowReason;
        private String returnDate;
        private Equipment equipment;

        public String getId() {
            return id;
        }

        public Equipment getEquipment(){
            return equipment;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getBrand() {
            return brand;
        }

        public String getOwner() {
            return owner;
        }

        public String getCondition() {
            return condition;
        }

        public String getBorrower() {
            return borrower;
        }

        public String getBorrowReason() {
            return borrowReason;
        }

        public String getReturnDate() {
            return returnDate;
        }

        public EquipmentRow(String id, String type, String name, String brand, String owner, String condition, String borrower, String borrowReason, String returnDate,Equipment equipment) {
            this.id = id;
            this.type = type;
            this.name = name;
            this.brand = brand;
            this.owner = owner;
            this.condition = condition;
            this.borrower = borrower;
            this.borrowReason = borrowReason;
            this.returnDate = returnDate;
            this.equipment = equipment;
        }
    }
}
