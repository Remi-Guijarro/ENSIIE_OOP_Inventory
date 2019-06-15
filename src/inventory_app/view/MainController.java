package inventory_app.view;

import inventory_app.Main;
import inventory_app.model.inventory.Borrower;
import inventory_app.model.users.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {

    @FXML
    private TabPane tabs;


    public void exit(){
        Main.mainPrimaryStage.close();
    }

    public void minimize(){
        Main.mainPrimaryStage.setIconified(true);
    }

    public void maximize(){
        if(Main.mainPrimaryStage.isMaximized()){
            Main.mainPrimaryStage.setMaximized(false);
        }else{
            Main.mainPrimaryStage.setMaximized(true);
        }
    }

    public void openUserView() {
        try {
            if(!tabs.getTabs().stream().anyMatch(tab -> tab.getText().equalsIgnoreCase("USERS"))){
                Parent root = FXMLLoader.load(this.getClass().getResource("userTab.fxml"));
                TableView table = (TableView) root.lookup("#usersTab");
                /*TableColumn<Borrower,String> column = (TableColumn<Borrower,String>) root.lookup("nameUser");*/
                table.getItems().addAll(Main.contextContainer.getUsers().get());
                Tab tab = new Tab();
                tab.setContent(root.lookup("#userAnchor"));
                tab.setText("USERS");
                tabs.getTabs().add(tab);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
