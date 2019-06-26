package inventory_app.view.tabs.inventory;

import inventory_app.Main;
import inventory_app.model.inventory.Borrowable;
import inventory_app.model.inventory.Borrower;
import inventory_app.model.inventory.Borrowing;
import inventory_app.model.inventory.Equipment;
import inventory_app.model.inventory.equipements.DepthSensor;
import inventory_app.model.inventory.equipements.Smartphone;
import inventory_app.model.inventory.equipements.Tablet;
import inventory_app.model.inventory.equipements.VRHeadset;
import inventory_app.view.tabs.inventory.addView.AddBorrowingViewController;
import inventory_app.view.tabs.inventory.detailedView.EquipmentDetailedController;
import inventory_app.view.tabs.inventory.filter.EquipmentAttributesFilterController;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.reflections.Reflections;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;

public class InventoryTableController implements Initializable {

    private Reflections reflections;
    private final String AVAILABLE = "";

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
    private TableColumn<EquipmentRow,Borrower>  itemBorrowerColumn;

    @FXML
    private TableColumn<EquipmentRow,String> borrowReasonColumn;

    @FXML
    private TableColumn<EquipmentRow,Date> returnDateColumn;

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

    private static ObservableList<EquipmentRow> equipmentRows;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

                if (newValue.equals("YES") && equipmentRow.getBorrower() != null) {
                    return true;
                }

                if (newValue.equals("NO") && equipmentRow.getBorrower() == null) {
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
                if (equipmentRow.getBorrower() != null
                        && equipmentRow.getBorrower().getName().toLowerCase().contains(lowerCaseFilter)) {
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
        typeFilterCombo.getSelectionModel().selectedItemProperty().addListener( ((observable, oldValue, newValue) -> {
            filteredData.setPredicate(equipmentRow -> {

                if (newValue.equals(Equipment.class))
                    return true;

                if (newValue == null) {
                    return false;
                }

                if (equipmentRow.getType().equals(newValue.getSimpleName())) {
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
        typeFilterCombo.getSelectionModel().selectFirst();
    }

    private void setTotalCountLabel(){
        totalCountLabel.setText("Total Count: " + equipmentTable.getItems().size());

    }

    private void displayNamesInTypeFilterCombo() {
        typeFilterCombo.setConverter(new StringConverter<Class>() {
            @Override
            public String toString(Class object) {
                if(object.getSimpleName().equalsIgnoreCase(Equipment.class.getSimpleName())){
                    return "Type";
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
    private void openAddEquipmentView() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("addView/addEquipmentView.fxml"));
        loader.load();
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
        itemBorrowerColumn.setCellFactory( column -> {
            TableCell<EquipmentRow, Borrower> cell = new TableCell<EquipmentRow, Borrower>() {
                @Override
                protected void updateItem(Borrower item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        // HERE TO CHANGE TEXT WHEN NO BORROWER
                        setText(null);
                    } else {
                        setText(item.getName());
                    }
                }
            };

            return cell;
        });
        itemBorrowerColumn.setCellValueFactory(new PropertyValueFactory<>("borrower"));
        borrowReasonColumn.setCellValueFactory(new PropertyValueFactory<>("borrowReason"));
        returnDateColumn.setCellFactory(column -> {
            TableCell<EquipmentRow, Date> cell = new TableCell<EquipmentRow, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty|| item == null) {
                        setText(null);
                    }
                    else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    public void setContextMenuOnTable(){
        MenuItem giveItBackMenu = new MenuItem("Give equipment back");
        giveItBackMenu.setOnAction((ActionEvent event) -> {
            EquipmentRow item = ((EquipmentRow)  equipmentTable.getSelectionModel().getSelectedItem());
            if(item.getReturnDate() != null &&
                    item.getBorrower() != null &&
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
        if(!equipmentTable.getItems().isEmpty()){
            equipmentTable.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        }

        Main.contextContainer.getInventoryManager().getAll().stream().filter(item -> type.isInstance(item)).forEach(equipment -> {
            Borrowing borrowing =  Main.contextContainer.getBorrowingsList().getBorrowerFrom(((Borrowable)equipment));
            String borrowReason = AVAILABLE;
            Borrower borrower = null;
            Date returnDate = null;

            if(borrowing != null){
                borrowReason = borrowing.getBorrowReason();
                borrower = borrowing.getBorrower();
                returnDate = borrowing.getReturnDate();

            }
            equipmentRows.add(new EquipmentRow(equipment.getReference(),
                    equipment.getClass().getSimpleName(),
                    equipment.getName(),
                    equipment.getBrand(),
                    equipment.getOwner().getName(),
                    equipment.getCondition().toString(),
                    borrower,
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
                    loadDetailedView(rowData.getType(), rowData.getEquipment());
                }
            });
            return row ;
        });

        ObservableList<EquipmentRow> observableList = FXCollections.observableArrayList(equipmentRows);
        observableList.addListener(new ListChangeListener<EquipmentRow>() {
            @Override
            public void onChanged(Change<? extends EquipmentRow> c) {
                equipmentTable.refresh();
                updateTotalCountLabel(c.getList().size());
            }
        });
        equipmentTable.setItems(observableList);
        setTotalCountLabel();
        InventoryTableController.equipmentRows = equipmentTable.getItems();

    }

    private void updateTotalCountLabel(int size) {
        this.totalCountLabel.setText("Total Count: " + size);
    }

    private void loadDetailedView(String type, Equipment equipment) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("detailedView/equipmentDetailedView.fxml"));
        try {
            loader.load();
            EquipmentDetailedController controller = loader.getController();
            controller.setTypeLabel(type);
            controller.setEquipment(equipment);
            controller.setDetailedInfo();
            if (type.equals("Smartphone")) {
                addSmarphoneFields(controller);
            } else if (type.equals("Tablet")) {
                addTabletFields(controller);
            } else if (type.equals("VRHeadset")) {
                addVRHeadsetFields(controller);
            } else if (type.equals("DepthSensor")) {
                addDepthSensorFields(controller);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addDepthSensorFields(EquipmentDetailedController controller) {
        //NO ADDITIONAL FIELD
    }

    private void addVRHeadsetFields(EquipmentDetailedController controller) {
        //NO ADDITIONAL FIELD
    }

    private void addTabletFields(EquipmentDetailedController controller) {
        Tablet tablet = (Tablet) controller.getEquipment();
        Label OSLabel = new Label("OS");
        TextField OSField = new TextField();
        OSField.setText(tablet.getOS().toString());
        controller.addTextField(OSLabel, OSField);

        Label resolutionLabel = new Label("Resolution");
        TextField resolutionField = new TextField();
        String resolution = String.valueOf(tablet.getResolution()[0]);
        resolution += "x";
        resolution += String.valueOf(tablet.getResolution()[1]);
        resolutionField.setText(resolution);
        controller.addTextField(resolutionLabel, resolutionField);
    }

    private void addSmarphoneFields(EquipmentDetailedController controller) {
        Smartphone smartphone = (Smartphone) controller.getEquipment();
        Label OSLabel = new Label("OS");
        TextField OSField = new TextField();

        OSField.setText(smartphone.getPHONE_OS().toString());
        controller.addTextField(OSLabel, OSField);

        Label screenSizeLabel = new Label("Screen Size");
        TextField screenSizeField = new TextField();
        screenSizeField.setText(String.valueOf(smartphone.getScreenSize()));
        controller.addTextField(screenSizeLabel, screenSizeField);
    }

    public static void addEquipment(Equipment e) {
        Main.contextContainer.getInventoryManager().addEquipment(e);
        EquipmentRow row = new EquipmentRow(e.getReference(), e.getClass().getSimpleName(),
                e.getName(), e.getBrand(), e.getOwner().getName(), e.getCondition().toString(),
                null,null, null, e.getLocation(), e
        );
        equipmentRows.add(row);
    }

    public TableView<EquipmentRow> getTableView() {
        return this.equipmentTable;
    }

    @FXML
    private void displayAttributes() {
        loadEquipmentAttributesFilterView();
    }

    private void loadEquipmentAttributesFilterView() {
        specificFilterVBox.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("filter/equipmentAttributesFilterView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            specificFilterVBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        EquipmentAttributesFilterController controller = loader.getController();
        addAttributesFields(controller);
    }

    private void addAttributesFields(EquipmentAttributesFilterController controller) {
        System.out.println(typeFilterCombo.getSelectionModel().getSelectedItem().getSimpleName());
        if (typeFilterCombo.getSelectionModel().getSelectedItem().equals(Smartphone.class)) {
            addSmarphoneAttributeFields(controller);
        } else if (typeFilterCombo.getSelectionModel().getSelectedItem().equals(Tablet.class)) {
            // Quick fix: when filter by type multiple times, table becomes empty
            addTabletAttributeFields(controller);
        } else if(typeFilterCombo.getSelectionModel().getSelectedItem().equals(Equipment.class)){
            populateTableBy(Equipment.class);
            setSearchFilter();
            setConditionFilterCombo();
            setIsBorrowedFilterCombo();
            setLocationFilter();
            setTypeFilter();
            setDisplayLateFilter();
        }
    }

    private void addTabletAttributeFields(EquipmentAttributesFilterController controller) {
        Label OSLabel = new Label("OS");
        ComboBox<Tablet.OS> OSCombo = new ComboBox<>();
        OSCombo.getItems().setAll(Tablet.OS.values());
        controller.addComboFilter(OSLabel, OSCombo);
        setFilterTabletOS(OSCombo);

        Label resolutionLabel = new Label("Resolution");
        TextField resolutionField = new TextField();
        resolutionField.setPromptText("1920x1080");
        controller.addTextFieldFilter(resolutionLabel, resolutionField);
        setFilterTabletResolution(resolutionField);
    }

    private void setFilterTabletResolution(TextField resolutionField) {
        ObservableList<EquipmentRow> allData = equipmentTable.getItems();
        FilteredList<EquipmentRow> filteredData = new FilteredList<>(allData, p -> true);
        resolutionField.textProperty().addListener( (observable, oldValue, newValue) -> {
            filteredData.setPredicate( equipmentRow -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                if (equipmentRow.getType().equals("Tablet")) {
                    Tablet tablet = (Tablet)equipmentRow.getEquipment();
                    String[] resolution = newValue.split("x");
                    if (tablet.getResolution()[0] == Integer.valueOf(resolution[0]) &&
                            tablet.getResolution()[1] == Integer.valueOf(resolution[1])) {
                        return true;
                    }
                }


                return false;
            });
        });

        SortedList<EquipmentRow> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(equipmentTable.comparatorProperty());

        equipmentTable.setItems(sortedData);

        addCountListener(sortedData);
    }

    private void setFilterTabletOS(ComboBox<Tablet.OS> osCombo) {
        if(osCombo.getItems().isEmpty())
            populateConditionCombo();
        ObservableList<EquipmentRow> allData = equipmentTable.getItems();
        FilteredList<EquipmentRow> filteredData = new FilteredList<>(allData, p -> true);
        osCombo.getSelectionModel().selectedItemProperty().addListener( ((observable, oldValue, newValue) -> {
            filteredData.setPredicate(equipmentRow -> {
                if (newValue == null) {
                    return false;
                }

                if (equipmentRow.getType().equals("Tablet") &&
                        ((Tablet)equipmentRow.getEquipment()).getOS() == newValue) {
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

    private void addSmarphoneAttributeFields(EquipmentAttributesFilterController controller) {
        Label OSLabel = new Label("OS");
        ComboBox<Smartphone.PHONE_OS> OSCombo = new ComboBox<>();
        OSCombo.getItems().setAll(Smartphone.PHONE_OS.values());
        controller.addComboFilter(OSLabel, OSCombo);
        setFilterSmartphoneOS(OSCombo);

        Label screenSizeLabel = new Label("Screen Size");
        TextField screenSizeField = new TextField();
        screenSizeField.setPromptText("in inches");
        controller.addTextFieldFilter(screenSizeLabel, screenSizeField);
        setFilterSmartphoneScreenSize(screenSizeField);
    }

    private void setFilterSmartphoneScreenSize(TextField screenSizeField) {
        ObservableList<EquipmentRow> allData = equipmentTable.getItems();
        FilteredList<EquipmentRow> filteredData = new FilteredList<>(allData, p -> true);
        screenSizeField.textProperty().addListener( (observable, oldValue, newValue) -> {
            filteredData.setPredicate( equipmentRow -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                if (equipmentRow.getType().equals("Smartphone")
                        && Double.valueOf(
                            ((Smartphone)equipmentRow.getEquipment()).getScreenSize()
                        ).equals(Double.valueOf(newValue))) {
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

    private void setFilterSmartphoneOS(ComboBox<Smartphone.PHONE_OS> osCombo) {
        if(osCombo.getItems().isEmpty())
            populateConditionCombo();
        ObservableList<EquipmentRow> allData = equipmentTable.getItems();
        FilteredList<EquipmentRow> filteredData = new FilteredList<>(allData, p -> true);
        osCombo.getSelectionModel().selectedItemProperty().addListener( ((observable, oldValue, newValue) -> {
            filteredData.setPredicate(equipmentRow -> {
                if (newValue == null) {
                    return false;
                }

                if (equipmentRow.getType().equals("Smartphone") &&
                        ((Smartphone)equipmentRow.getEquipment()).getPHONE_OS() == newValue) {
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

    public static class EquipmentRow {
        private String id;
        private String type;
        private String name;
        private String brand;
        private String owner;
        private String condition;
        private Borrower borrower;
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

        public Borrower getBorrower() {
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

        public EquipmentRow(String id, String type, String name, String brand, String owner, String condition, Borrower borrower, String borrowReason, Date returnDate, Equipment.Location location, Equipment equipment) {
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
