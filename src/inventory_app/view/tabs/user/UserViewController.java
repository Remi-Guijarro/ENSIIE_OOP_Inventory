package inventory_app.view.tabs.user;

import inventory_app.Main;
import inventory_app.model.inventory.Borrower;
import inventory_app.model.inventory.Startup;
import inventory_app.model.users.People;
import inventory_app.view.tabs.user.detailedView.CompanyTableViewDetailController;
import inventory_app.view.tabs.user.detailedView.PeopleTableViewDetailController;
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

public class UserViewController implements Initializable {
    @FXML
    private AnchorPane userAnchor;

    @FXML
    private TableColumn nameUser;

    @FXML
    private TableView usersTab;

    @FXML
    private BorderPane detailedInfo;

    @FXML
    private Button addButton;

    @FXML
    private TextField searchText;

    @FXML
    private Button searchButton;

    public TableColumn getNameUser(){
        return nameUser;
    }

    public TableView getUsersTab(){
        return usersTab;
    }

    public AnchorPane getUserAnchor(){
        return userAnchor;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //searchButton.setText("");
        searchButton.setGraphic(new ImageView(new Image(this.getClass().getResource("../../../../icons/search.png").toExternalForm())));
        nameUser.setCellValueFactory(new PropertyValueFactory<>("name"));
        usersTab.getItems().addAll(Main.contextContainer.getUsers().get());
        usersTab.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue instanceof Startup){
                        FXMLLoader loader = null;
                        try {
                            loader = loadDetailsView("detailedView/companyTableViewDetail.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ((CompanyTableViewDetailController)loader.getController()).setDetailedInfo((Borrower) newValue);
                    } else if (newValue instanceof People){
                        FXMLLoader loader = null;
                        try {
                            loader = loadDetailsView("detailedView/peopleTableViewDetail.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ((PeopleTableViewDetailController)loader.getController()).setDetailedInfo((Borrower) newValue);
                    }
                });
    }

    private FXMLLoader loadDetailsView(String location) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(location));
        Parent root =  loader.load();
        AnchorPane rootBorderPane = (AnchorPane) root;
        detailedInfo.setCenter(rootBorderPane);
        return loader;
    }
}
