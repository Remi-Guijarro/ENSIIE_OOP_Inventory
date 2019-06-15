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
        if(!tabs.getTabs().stream().anyMatch(tab -> tab.getText().equalsIgnoreCase("USERS"))){
            try {
                Tab tab = new Tab();
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(this.getClass().getResource("userTab.fxml"));
                tab.setContent(root);
                tab.setText("USERS");
                tabs.getTabs().add(tab);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
