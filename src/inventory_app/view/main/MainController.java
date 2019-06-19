package inventory_app.view.main;

import inventory_app.Main;
import inventory_app.model.inventory.Borrower;
import inventory_app.model.users.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TabPane tabs;

    @FXML
    private Button min;

    @FXML
    private Button max;

    @FXML
    private Button close;

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
                Parent root = loader.load(this.getClass().getResource("../tabs/user/userListView.fxml"));
                tab.setContent(root);
                tab.setText("USERS");
                tabs.getTabs().add(tab);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        min.setGraphic(new ImageView(new Image(this.getClass().getResource("../../../icons/minimize.png").toExternalForm())));
        max.setGraphic(new ImageView(new Image(this.getClass().getResource("../../../icons/maximize.png").toExternalForm())));
        close.setGraphic(new ImageView(new Image(this.getClass().getResource("../../../icons/close.png").toExternalForm())));
    }
}
