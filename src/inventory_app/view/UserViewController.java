package inventory_app.view;

import inventory_app.Main;
import inventory_app.model.inventory.Borrower;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {
    @FXML
    private AnchorPane userAnchor;

    @FXML
    private TableColumn nameUser;

    @FXML
    private TableView usersTab;

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
        nameUser.setCellValueFactory(new PropertyValueFactory<>("name"));
        usersTab.getItems().addAll(Main.contextContainer.getUsers().get());
    }
}
