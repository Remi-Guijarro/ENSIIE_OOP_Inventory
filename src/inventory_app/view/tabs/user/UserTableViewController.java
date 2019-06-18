package inventory_app.view.tabs.user;

import inventory_app.Main;
import inventory_app.model.inventory.Borrower;
import inventory_app.model.inventory.Startup;
import inventory_app.model.users.People;
import inventory_app.view.tabs.user.detailedView.CompanyTableViewDetailedController;
import inventory_app.view.tabs.user.detailedView.PeopleTableViewDetailedController;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserTableViewController implements Initializable {
    @FXML
    private AnchorPane userAnchor;

    @FXML
    private TableColumn<Borrower, String> nameColumn;

    @FXML
    private TableView<Borrower> borrowerTable;

    @FXML
    private BorderPane detailedInfo;

    @FXML
    private Button addButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    public TableColumn getNameColumn(){
        return nameColumn;
    }

    public TableView getBorrowerTable(){
        return borrowerTable;
    }

    public AnchorPane getUserAnchor(){
        return userAnchor;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setIcons();
        populateTable();
        setColumnName();
        setListenerOnTableItems();
        setSearchFilter();
    }

    private void setListenerOnTableItems() {
        borrowerTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue instanceof Startup){
                        FXMLLoader loader = null;
                        try {
                            loader = loadDetailsView("detailedView/companyTableViewDetailed.fxml");
                            ((CompanyTableViewDetailedController)loader.getController()).setDetailedInfo((Borrower) newValue);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (newValue instanceof People){
                        FXMLLoader loader = null;
                        try {
                            loader = loadDetailsView("detailedView/peopleTableViewDetailed.fxml");
                            ((PeopleTableViewDetailedController)loader.getController()).setDetailedInfo((Borrower) newValue);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    private void setColumnName() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void populateTable() {
        borrowerTable.getItems().addAll(Main.contextContainer.getUsers().get());
    }

    private void setIcons() {
        searchButton.setGraphic(new ImageView(new Image(this.getClass().getResource("../../../../icons/search.png").toExternalForm())));
    }

    private void setSearchFilter() {
        ObservableList<Borrower> allData = borrowerTable.getItems();
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

        sortedData.comparatorProperty().bind(borrowerTable.comparatorProperty());

        borrowerTable.setItems(sortedData);
    }

    private FXMLLoader loadDetailsView(String location) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(location));
        Parent root =  loader.load();
        AnchorPane rootBorderPane = (AnchorPane) root;
        detailedInfo.setCenter(rootBorderPane);
        return loader;
    }
}
