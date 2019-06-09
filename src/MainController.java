import model.inventory.Borrower;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class MainController {
    @FXML
    private TableColumn tColUserName;

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

    public void displayUsers(){
        if(tColUserName.isVisible()){
            tColUserName.setVisible(false);
        }else {
            tColUserName.setVisible(true);
            ArrayList<Borrower> borrower = Main.contextContainer.getUsers().get();
            borrower.forEach(item -> tColUserName.setCellFactory(new PropertyValueFactory<>(item.getName())));
        }
    }
}
