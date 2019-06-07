import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import inventory.Borrower;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {

    @FXML
    private ListView<String> listUsers;
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
        ArrayList<Borrower> borrower = Main.contextContainer.getUsers().get();
        ArrayList<String> borrowersName =new ArrayList<>();
        borrower.forEach(item -> borrowersName.add(item.getName()));
        ObservableList<String> fruitList = FXCollections.<String>observableArrayList(borrowersName);
        listUsers.setItems(fruitList);
        listUsers.setOrientation(Orientation.VERTICAL);
        System.out.println(listUsers.isVisible());
    }
}
