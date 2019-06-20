package inventory_app.view.tabs.inventory;

import inventory_app.Main;
import inventory_app.model.inventory.Borrowable;
import inventory_app.model.inventory.Borrower;
import inventory_app.model.inventory.Borrowing;
import inventory_app.model.inventory.Equipment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.omg.CORBA.MARSHAL;

import java.net.URL;
import java.util.ResourceBundle;

public class InventoryTableController implements Initializable {

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setColumnProperty();
        populateTable();
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

    private void populateTable(){
        Main.contextContainer.getInventoryManager().getAll().forEach(equipment -> {
            Borrowing borrowing =  Main.contextContainer.getBorrowingsList().getBorrowerFrom(((Borrowable)equipment));
            //  borrowing.getBorrower() == null ? "null" : borrowing.getBorrower().getName()
            // borrowing.getBorrowReason()
            //borrowing.getReturnDate().toString()
            equipmentTable.getItems().add(new EquipmentRow(equipment.getReference(),
                    equipment.getClass().getSimpleName(),
                    equipment.getName(),
                    equipment.getBrand(),
                    equipment.getOwner().getName(),
                    equipment.getCondition().toString(),
                   "null",
                    "null",
                    "null")
            );
        });
    }


    public class EquipmentRow {
        @FXML
        private String id;

        @FXML
        private String type;

        @FXML
        private String name;

        @FXML
        private String brand;

        @FXML
        private String owner;

        @FXML
        private String condition;

        @FXML
        private String borrower;

        @FXML
        private String borrowReason;

        @FXML
        private String returnDate;

        public String getId() {
            return id;
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

        public EquipmentRow(String id, String type, String name, String brand, String owner, String condition, String borrower, String borrowReason, String returnDate) {
            this.id = id;
            this.type = type;
            this.name = name;
            this.brand = brand;
            this.owner = owner;
            this.condition = condition;
            this.borrower = borrower;
            this.borrowReason = borrowReason;
            this.returnDate = returnDate;
        }
    }
}
