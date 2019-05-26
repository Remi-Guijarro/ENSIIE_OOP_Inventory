import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.io.IOException;

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
