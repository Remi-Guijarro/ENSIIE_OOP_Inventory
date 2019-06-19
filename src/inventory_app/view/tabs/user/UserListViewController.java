package inventory_app.view.tabs.user;

import inventory_app.Main;
import inventory_app.model.inventory.Borrower;
import inventory_app.model.inventory.Startup;
import inventory_app.model.users.People;
import inventory_app.view.tabs.user.detailedView.CompanyListViewDetailedController;
import inventory_app.view.tabs.user.detailedView.PeopleListViewDetailedController;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserListViewController implements Initializable {
    @FXML
    private AnchorPane userAnchor;

    @FXML
    private  TableColumn<Borrower, String> nameColumn;

    @FXML
    private ListView<Borrower> borrowerList;

    @FXML
    private VBox detailedInfo;

    @FXML
    private Button addButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    public TableColumn getNameColumn(){
        return nameColumn;
    }

    public ListView getBorrowerList(){
        return borrowerList;
    }

    public AnchorPane getUserAnchor(){
        return userAnchor;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setIcons();
        populateTable();
        //setColumnName();
        setListenerOnTableItems();
        setSearchFilter();
    }

    private void setListenerOnTableItems() {
        borrowerList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue instanceof Startup){
                        FXMLLoader loader = null;
                        try {
                            loader = loadDetailsView("detailedView/companyListViewDetailed.fxml");
                            ((CompanyListViewDetailedController)loader.getController()).setDetailedInfo((Borrower) newValue);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (newValue instanceof People){
                        FXMLLoader loader = null;
                        try {
                            loader = loadDetailsView("detailedView/peopleListViewDetailed.fxml");
                            ((PeopleListViewDetailedController)loader.getController()).setDetailedInfo((Borrower) newValue,borrowerList);
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
        borrowerList.getItems().addAll(Main.contextContainer.getUsers().get());
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

    private void setIcons() {
        searchButton.setGraphic(new ImageView(new Image(this.getClass().getResource("../../../../icons/search.png").toExternalForm())));
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

    private void emptyDetailInfo() {
        if (!detailedInfo.getChildren().isEmpty())
            detailedInfo.getChildren().remove(0);
    }

    private FXMLLoader loadDetailsView(String location) throws IOException {
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
