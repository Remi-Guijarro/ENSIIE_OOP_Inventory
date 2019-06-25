package inventory_app.view.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
                Parent root = loader.load(this.getClass().getClassLoader().getResource("inventory_app/view/tabs/user/userListView.fxml"));
                tab.setContent(root);
                tab.setText("USERS");
                tabs.getTabs().add(tab);
                tabs.getSelectionModel().select(tab);
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
                Parent root = loader.load(this.getClass().getClassLoader().getResource("inventory_app/view/tabs/inventory/inventoryTableView.fxml"));
                tab.setContent(root);
                tab.setText("INVENTORY");
                tabs.getTabs().add(tab);
                tabs.getSelectionModel().select(tab);
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
