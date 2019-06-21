package inventory_app.view.tabs.user.addView;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AddPeopleViewController {

    @FXML
    private Label typeLabel;



    public void setTypeLabelText(String text) {
        typeLabel.setText(text);
    }
}
