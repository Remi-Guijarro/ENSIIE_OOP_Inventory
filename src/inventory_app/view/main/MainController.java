package inventory_app.view.main;

import inventory_app.Main;
import inventory_app.model.inventory.Borrower;
import inventory_app.model.users.Student;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {



    @FXML
    private TabPane tabs;

    @FXML
    private SplitPane mainSplitPane;

    @FXML
    private ProgressBar progressBar;



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


    public void openStorageView(){
        if(!tabs.getTabs().stream().anyMatch(tab -> tab.getText().equalsIgnoreCase("INVENTORY"))){
            progressBar.setVisible(true);
            progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            try {
                Tab tab = new Tab();
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(this.getClass().getResource("../tabs/inventory/inventoryTableView.fxml"));
                tab.setContent(root);
                tab.setText("INVENTORY");
                tabs.getTabs().add(tab);
            } catch (IOException e) {
                e.printStackTrace();
            }
            progressBar.setProgress(0);
            progressBar.setVisible(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progressBar.setVisible(false);
    }
}
