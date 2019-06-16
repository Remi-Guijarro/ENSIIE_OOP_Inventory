package inventory_app.view.tabs.user;

import inventory_app.Main;
import inventory_app.model.inventory.Borrower;
import inventory_app.view.tabs.user.detailedView.PeopleTableViewDetailController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
        try {
            nameUser.setCellValueFactory(new PropertyValueFactory<>("name"));
            usersTab.getItems().addAll(Main.contextContainer.getUsers().get());
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("detailedView/peopleTableViewDetail.fxml"));
            Parent root =  loader.load();
            AnchorPane rootAnchorPane = (AnchorPane) root;
            detailedInfo.setCenter(rootAnchorPane);
            usersTab.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> ((PeopleTableViewDetailController)loader.getController()).setDetailedInfo((Borrower) newValue));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
