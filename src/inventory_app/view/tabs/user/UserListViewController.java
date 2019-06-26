package inventory_app.view.tabs.user;

import inventory_app.Main;
import inventory_app.model.inventory.Borrower;
import inventory_app.model.inventory.Startup;
import inventory_app.model.users.People;
import inventory_app.view.tabs.user.detailedView.CompanyListViewDetailedController;
import inventory_app.view.tabs.user.detailedView.PeopleListViewDetailedController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class UserListViewController implements Initializable {

    @FXML
    private ComboBox<Class> typeFilterCombo;

    private static ObservableList<Borrower> borrowers;

    @FXML
    private ListView<Borrower> borrowerList;

    @FXML
    private VBox detailedInfo;

    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setObservableList();
        populateTable();
        setListenerOnTableItems();
        setSearchFilter();
        setTypeFilter();
    }

    private void setObservableList() {
        borrowers = FXCollections.observableArrayList(Main.contextContainer.getUsers().get());
        borrowers.addListener(new ListChangeListener<Borrower>() {
            @Override
            public void onChanged(Change<? extends Borrower> c) {
                borrowerList.refresh();
            }
        });
    }

    private void setListenerOnTableItems() {
        borrowerList.getSelectionModel().selectedItemProperty().addListener(
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
                            ((PeopleListViewDetailedController)loader.getController()).setDetailedInfo((Borrower) newValue,borrowerList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    public static void addUser(Borrower b) {
        Main.contextContainer.getUsers().addUser(b);
        borrowers.add(b);
    }

    public static ObservableList<Borrower> getBorrowers() {
        return borrowers;
    }

    private void  populateTable() {
        borrowerList.setItems(getBorrowers());
        borrowerList.setCellFactory( param -> new ListCell<Borrower>() {
            @Override
            protected void updateItem(Borrower borrower, boolean empty) {
                super.updateItem(borrower, empty);
                if (empty || borrower == null || borrower.getName() == null) {
                    setText(null);
                } else {
                    setText(borrower.getName());
                }
            }
        });
    }

    private void setSearchFilter() {
        ObservableList<Borrower> allData = borrowerList.getItems();
        FilteredList<Borrower> filteredData = new FilteredList<>(allData, p -> true);
        searchField.textProperty().addListener( (observable, oldValue, newValue) -> {
            filteredData.setPredicate( borrower -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (borrower.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Borrower> sortedData = new SortedList<>(filteredData);

        //sortedData.comparatorProperty().bind(borrowerList.comparatorProperty());

        borrowerList.setItems(sortedData);
    }

    private void setTypeFilter(){
        typeFilterCombo.getItems().add(Borrower.class);
        ObservableList<Borrower> allData = borrowerList.getItems();
        FilteredList<Borrower> filteredData = new FilteredList<>(allData, p -> true);
        Set<Class> types = new HashSet<>();
        borrowerList.getItems().forEach(item -> {
            types.add(item.getClass());
        });
        typeFilterCombo.getItems().addAll(types);
        typeFilterCombo.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
            filteredData.setPredicate( borrower -> {
                if (newValue == null) {
                    return true;
                } else {
                    if (newValue.isInstance(borrower)) {
                        return true;
                    }
                    return false;
                }
            });
        });
        borrowerList.setItems(new SortedList<>(filteredData));
        displayNamesInCombo();

        typeFilterCombo.setValue(Borrower.class);
    }

    private void displayNamesInCombo() {
        typeFilterCombo.setConverter(new StringConverter<Class>() {

            @Override
            public String toString(Class object) {
                //Special case: if class is Borrower, we want the combobox to display 'All'
                if (object.getSimpleName().equals("Borrower")) {
                    return "All";
                }

                return object.getSimpleName();
            }

            @Override
            public Class fromString(String string) {
                return typeFilterCombo.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void emptyDetailInfo() {
        if (!detailedInfo.getChildren().isEmpty())
            detailedInfo.getChildren().remove(0);
    }

    private FXMLLoader loadDetailedView(String location) throws IOException {
        emptyDetailInfo();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(location));
        Parent root =  loader.load();
        detailedInfo.getChildren().add(root);

        return loader;
    }

    public void openAddBorrowerView() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("addView/addBorrowerView.fxml"));
        loader.load();
    }
}
