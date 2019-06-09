package inventory_app.view;

import inventory_app.Main;
import inventory_app.model.inventory.Borrower;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class MainController {

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
}
