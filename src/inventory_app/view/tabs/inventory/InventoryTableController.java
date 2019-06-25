package inventory_app.view.tabs.inventory;

import inventory_app.Main;
import inventory_app.model.inventory.Borrowable;
import inventory_app.model.inventory.Borrowing;
import inventory_app.model.inventory.Equipment;
import inventory_app.view.tabs.inventory.addView.AddBorrowingViewController;
import inventory_app.view.tabs.inventory.detailedView.utils.EquipmentDetailedController;
import inventory_app.view.tabs.inventory.filter.utils.FilterController;
import inventory_app.view.tabs.inventory.filter.utils.FilterControllerLoader;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.reflections.Reflections;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;

public class InventoryTableController implements Initializable {

    private Reflections reflections;
    private final String AVAILABLE = "Available";

    @FXML
    private TableView<EquipmentRow> equipmentTable;

    @FXML
    private TableColumn<EquipmentRow,String> idItemColumn;

    @FXML
    private TableColumn<EquipmentRow,String> itemTypeColumn;

    @FXML
    private TableColumn<EquipmentRow,String> itemNameColumn;

    @FXML
    private TableColumn<EquipmentRow,String> itemBrandColumn;

    @FXML
    private TableColumn<EquipmentRow,String> itemOwnerColumn;

    @FXML
    private TableColumn<EquipmentRow,String> itemConditionColumn;

    @FXML
    private TableColumn<EquipmentRow,String>  itemBorrowerColumn;

    @FXML
    private TableColumn<EquipmentRow,String> borrowReasonColumn;

    @FXML
    private TableColumn<EquipmentRow,String> returnDateColumn;

    @FXML
    private TableColumn<EquipmentRow, Equipment.Location> locationColumn;

    @FXML
    private ComboBox<Class> typeFilterCombo;

    @FXML
    private Label totalCountLabel;

    @FXML
    private Label currentCountLabel;

    @FXML
    private VBox specificFilterVBox;

    @FXML
    private ComboBox<String> conditionFilterCombo;

    @FXML
    private ComboBox<String> locationComboBox;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> isBorrowedCombo;

    @FXML
    private CheckBox displayLateCheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*//TEST
        try {
            Main.contextContainer.getBorrowingsList().
                    addBorrowedItem(Main.contextContainer.getInventoryManager().getAvailable().get(19),
                            new SimpleDateFormat("yyyy-MM-dd").parse("2019-06-02"),
                            "Why not",
                            Main.contextContainer.getUsers().get().get(19));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/


        setColumnProperty();
        equipmentTable.setEditable(true);
        populateTableBy(Equipment.class);
        setSearchFilter();
        setConditionFilterCombo();
        setIsBorrowedFilterCombo();
        setLocationFilter();
        setContextMenuOnTable();
        setTypeFilter();
        setDisplayLateFilter();
        updateCurrentCountLabel(equipmentTable.getItems().size());
    }

    private void setLocationFilter() {
        if(locationComboBox.getItems().isEmpty())
            populateLocationCombo();
        ObservableList<EquipmentRow> allData = equipmentTable.getItems();
        FilteredList<EquipmentRow> filteredData = new FilteredList<>(allData, p -> true);
        locationComboBox.getSelectionModel().selectedItemProperty().addListener( ((observable, oldValue, newValue) -> {
            filteredData.setPredicate(equipmentRow -> {

                if (newValue.equals("Location"))
                    return true;

                if (newValue == null || newValue.isEmpty()) {
                    return false;
                }

                if (equipmentRow.getLocation() == Equipment.Location.valueOf(newValue)) {
                    return true;
                }

                return false;
            });
        }));

        SortedList<EquipmentRow> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(equipmentTable.comparatorProperty());
        equipmentTable.setItems(sortedData);

        addCountListener(sortedData);
    }

    private void populateLocationCombo() {
        locationComboBox.getItems().add("Location");
        for (Equipment.Location l : Equipment.Location.values()) {
            locationComboBox.getItems().add(l.toString());
        }
    }

    private void setDisplayLateFilter() {
        ObservableList<EquipmentRow> allData = equipmentTable.getItems();
        FilteredList<EquipmentRow> filteredData = new FilteredList<>(allData, p -> true);
        displayLateCheckBox.selectedProperty().addListener( ((observable, oldValue, newValue) -> {
            filteredData.setPredicate(equipmentRow -> {

                if (!newValue)
                    return true;

                if (newValue && equipmentRow.returnDate != null && equipmentRow.isLate())
                    return true;

                return false;
            });
        }));

        SortedList<EquipmentRow> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(equipmentTable.comparatorProperty());
        equipmentTable.setItems(sortedData);

        addCountListener(sortedData);
    }

    private void setIsBorrowedFilterCombo() {
        if(isBorrowedCombo.getItems().isEmpty())
            populateIsBorrowedCombo();

        ObservableList<EquipmentRow> allData = equipmentTable.getItems();
        FilteredList<EquipmentRow> filteredData = new FilteredList<>(allData, p -> true);
        isBorrowedCombo.getSelectionModel().selectedItemProperty().addListener( ((observable, oldValue, newValue) -> {
            filteredData.setPredicate(equipmentRow -> {

                if (newValue.equals("Is Borrowed"))
                    return true;

                if (newValue == null || newValue.isEmpty()) {
                    return false;
                }

                if (newValue.equals("YES") && !equipmentRow.getBorrower().equals(AVAILABLE)) {
                    return true;
                }

                if (newValue.equals("NO") && equipmentRow.getBorrower().equals(AVAILABLE)) {
                    return true;
                }

                return false;
            });
        }));

        SortedList<EquipmentRow> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(equipmentTable.comparatorProperty());
        equipmentTable.setItems(sortedData);

        addCountListener(sortedData);
    }

    private void populateIsBorrowedCombo() {
        isBorrowedCombo.getItems().add("Is Borrowed");
        isBorrowedCombo.getItems().add("YES");
        isBorrowedCombo.getItems().add("NO");
    }

    private void setSearchFilter() {
        ObservableList<EquipmentRow> allData = equipmentTable.getItems();
        FilteredList<EquipmentRow> filteredData = new FilteredList<>(allData, p -> true);
        searchField.textProperty().addListener( (observable, oldValue, newValue) -> {
            filteredData.setPredicate( equipmentRow -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // By id
                if (equipmentRow.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                // By type
                if (equipmentRow.getType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                // By name
                if (equipmentRow.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                // By brand
                if (equipmentRow.getBrand().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                // By owner
                if (equipmentRow.getOwner().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                // By borrower
                if (equipmentRow.getBorrower().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                // By reason
                if (equipmentRow.getBorrowReason().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
        });

        SortedList<EquipmentRow> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(equipmentTable.comparatorProperty());

        equipmentTable.setItems(sortedData);

        addCountListener(sortedData);
    }

    private void setTypeFilter() {
        if(typeFilterCombo.getItems().isEmpty())
            populateTypeCombo();
        ObservableList<EquipmentRow> allData = equipmentTable.getItems();
        FilteredList<EquipmentRow> filteredData = new FilteredList<>(allData, p -> true);
        typeFilterCombo.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
            if(specificFilterVBox.getChildren().size() > 0)
                specificFilterVBox.getChildren().remove(0);
            if(!newValue.equals(Equipment.class)){
                FilterControllerLoader filterControllerLoader = new FilterControllerLoader();
                FXMLLoader fxmlLoader =  filterControllerLoader.loadFrom(newValue);
                Parent parent = null;
                try {
                    parent = fxmlLoader.load();
                    FilterController filterController = fxmlLoader.getController();
                    filterController.setTableView(equipmentTable);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                specificFilterVBox.getChildren().add(parent);
                filteredData.setPredicate( equipmentRow -> {
                    if (newValue == null) {
                        return true;
                    }
                    String classSimpleName = newValue.getSimpleName();

                    if (equipmentRow.getType().equalsIgnoreCase(classSimpleName)) {
                        return true;
                    }
                    return false;
                });
            }else {
                populateTableBy(Equipment.class);
            }
        });

        SortedList<EquipmentRow> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(equipmentTable.comparatorProperty());
        equipmentTable.setItems(sortedData);
        addCountListener(sortedData);
        setTotalCountLabel();
    }

    private void populateConditionCombo() {
        conditionFilterCombo.getItems().add("Condition");
        for (Equipment.Condition current : Equipment.Condition.values()) {
            conditionFilterCombo.getItems().add(current.toString());
        }
    }

    private void setConditionFilterCombo(){
        if(conditionFilterCombo.getItems().isEmpty())
            populateConditionCombo();
        ObservableList<EquipmentRow> allData = equipmentTable.getItems();
        FilteredList<EquipmentRow> filteredData = new FilteredList<>(allData, p -> true);
        conditionFilterCombo.getSelectionModel().selectedItemProperty().addListener( ((observable, oldValue, newValue) -> {
            filteredData.setPredicate(equipmentRow -> {

                if (newValue.equals("Condition"))
                    return true;

                if (newValue == null || newValue.isEmpty()) {
                    return false;
                }

                if (equipmentRow.getCondition().equals(newValue)) {
                    return true;
                }

                return false;
            });
        }));

        SortedList<EquipmentRow> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(equipmentTable.comparatorProperty());
        equipmentTable.setItems(sortedData);

        addCountListener(sortedData);
    }

    private void addCountListener(SortedList<EquipmentRow> sortedData) {
        sortedData.addListener((ListChangeListener<EquipmentRow>) c -> updateCurrentCountLabel(c.getList().size()));
    }

    private void updateCurrentCountLabel(int value) {
        currentCountLabel.setText("Current Count: "+value);
    }

    private void populateTypeCombo() {
        reflections = new Reflections(Equipment.class.getPackage().getName());
        Set<Class<? extends  Equipment>> classSet = reflections.getSubTypesOf(Equipment.class);
        typeFilterCombo.getItems().add(Equipment.class);
        typeFilterCombo.getItems().addAll(classSet);
        displayNamesInTypeFilterCombo();
    }

    private void setTotalCountLabel(){
        totalCountLabel.setText("Total Count: " + equipmentTable.getItems().size());

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
        openAddBorrowView("");
    }

    @FXML
    private void openAddEquipmentView(){
        System.out.println("COUCOU");
    }

    private void openAddBorrowView(String id) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("addView/addBorrowingView.fxml"));
        Parent node = loader.load();
        AddBorrowingViewController controller = loader.getController();
        controller.setTableController(this);
        controller.setDefaultReferenceValue(id);
        controller.openView();
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
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    public void setContextMenuOnTable(){
        MenuItem giveItBackMenu = new MenuItem("Give equipment back");
        giveItBackMenu.setOnAction((ActionEvent event) -> {
            EquipmentRow item = ((EquipmentRow)  equipmentTable.getSelectionModel().getSelectedItem());
            if(item.getReturnDate() != null &&
                    !item.getBorrower().equalsIgnoreCase(AVAILABLE) &&
                    !item.getBorrowReason().equalsIgnoreCase(AVAILABLE)){
                Main.contextContainer.getBorrowingsList().removeBorrowedItem(item.getEquipment());
                this.populateTableBy(Equipment.class);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Current equipment is not borrowed ");
                alert.show();
            }
        });

        MenuItem borrowContextMenu = new MenuItem("Borrow this item");
        borrowContextMenu.setOnAction((ActionEvent) -> {
            try {
                EquipmentRow item = ((EquipmentRow)  equipmentTable.getSelectionModel().getSelectedItem());
                openAddBorrowView(item.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ContextMenu menu = new ContextMenu();
        menu.getItems().add(giveItBackMenu);
        menu.getItems().add(borrowContextMenu);
        equipmentTable.setContextMenu(menu);
    }

    /**
     * This method populate the table by the given Class
     * @param type -> the wanted Class
     */
    public void populateTableBy(Class type){
        ArrayList<EquipmentRow> equipmentRows = new ArrayList<>();
        if(!equipmentTable.getItems().isEmpty())
            equipmentTable.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        Main.contextContainer.getInventoryManager().getAll().stream().filter(item -> type.isInstance(item)).forEach(equipment -> {
            Borrowing borrowing =  Main.contextContainer.getBorrowingsList().getBorrowerFrom(((Borrowable)equipment));
            String borrowReason = AVAILABLE;
            String borrowerName = AVAILABLE;
            Date returnDate = null;

            if(borrowing != null){
                borrowReason = borrowing.getBorrowReason();
                borrowerName = borrowing.getBorrower().getName();
                returnDate = borrowing.getReturnDate();

            }
            equipmentRows.add(new EquipmentRow(equipment.getReference(),
                    equipment.getClass().getSimpleName(),
                    equipment.getName(),
                    equipment.getBrand(),
                    equipment.getOwner().getName(),
                    equipment.getCondition().toString(),
                    borrowerName,
                    borrowReason,
                    returnDate,
                    equipment.getLocation(),
                    equipment)
            );
        });

        equipmentTable.setRowFactory( tv -> {
            TableRow<EquipmentRow> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    EquipmentRow rowData = row.getItem();
                    EquipmentDetailedController equipmentDetailedController= new EquipmentDetailedController();
                    FXMLLoader fxmlLoader =  equipmentDetailedController.loadFrom(rowData.getEquipment().getClass());
                    Parent parent = null;
                    try {
                        parent = fxmlLoader.load();
                        //FilterController filterController = fxmlLoader.getController();
                        //filterController.setTableView(equipmentTable);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage = new Stage();
                    stage.setTitle("Detailed View");
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.show();
                }
            });
            return row ;
        });

        equipmentTable.setItems(FXCollections.observableArrayList(equipmentRows));
        setTypeFilter();
        setConditionFilterCombo();
        setIsBorrowedFilterCombo();
        setSearchFilter();
        setTotalCountLabel();

    }

    @FXML
    private void onlyDisplayLate() {
        if (displayLateCheckBox.isSelected()) {

        } else {

        }
    }

    public TableView<EquipmentRow> getTableView() {
        return this.equipmentTable;
    }


    public static class EquipmentRow {
        private String id;
        private String type;
        private String name;
        private String brand;
        private String owner;
        private String condition;
        private String borrower;
        private String borrowReason;
        private Date returnDate;
        private Equipment.Location location;
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

        public Date getReturnDate() {
            return returnDate;
        }

        public Equipment.Location getLocation() {
            return location;
        }

        public EquipmentRow(String id, String type, String name, String brand, String owner, String condition, String borrower, String borrowReason, Date returnDate, Equipment.Location location, Equipment equipment) {
            this.id = id;
            this.type = type;
            this.name = name;
            this.brand = brand;
            this.owner = owner;
            this.condition = condition;
            this.borrower = borrower;
            this.borrowReason = borrowReason;
            this.returnDate = returnDate;
            this.location = location;
            this.equipment = equipment;
        }

        public boolean isLate() {
            Date now = new Date();
            return now.after(this.returnDate);
        }
    }
}
