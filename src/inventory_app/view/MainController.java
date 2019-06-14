package inventory_app.view;

import inventory_app.Main;
import inventory_app.model.inventory.Borrower;
import inventory_app.model.users.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

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
      /*  Tab userTab = new Tab();
        AnchorPane userTabRootNode = new AnchorPane();
        userTab.setContent(userTabRootNode);
        TableView userTableView = new TableView();
        TableColumn name = new TableColumn();
        name.setText("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        userTableView.getColumns().add(name);
        userTableView.prefWidthProperty().bind(userTabRootNode.prefWidthProperty());
        userTableView.prefHeightProperty().bind(userTabRootNode.prefHeightProperty());
        Main.contextContainer.getUsers().get().forEach(item -> userTableView.getItems().add(item));
        userTabRootNode.getChildren().add(userTableView);
        tabs.getTabs().add(userTab);
        */
    }
}
