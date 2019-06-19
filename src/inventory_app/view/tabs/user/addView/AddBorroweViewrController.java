package inventory_app.view.tabs.user.addView;

import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBorroweViewrController implements Initializable {
    private Stage addBorrowerStage;

    @FXML
    AnchorPane root;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button validateBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addBorrowerStage = new Stage();
        addBorrowerStage.initStyle(StageStyle.UNDECORATED);
        addBorrowerStage.setTitle("Add Borrower");
        BorderlessScene bordelessScene = new BorderlessScene(addBorrowerStage, StageStyle.UNDECORATED, root);
        addBorrowerStage.setScene(bordelessScene);
        bordelessScene.setMoveControl(root);
        bordelessScene.setSnapEnabled(false);
        bordelessScene.setResizable(false);
        bordelessScene.removeDefaultCSS();
        addBorrowerStage.setAlwaysOnTop(true);
        addBorrowerStage.show();
    }

    public void close(){
        addBorrowerStage.close();
    }
}
