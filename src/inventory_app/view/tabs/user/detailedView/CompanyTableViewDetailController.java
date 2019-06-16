package inventory_app.view.tabs.user.detailedView;

import inventory_app.model.inventory.Borrower;
import inventory_app.model.inventory.Startup;
import inventory_app.model.users.Student;
import inventory_app.model.users.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanyTableViewDetailController implements Initializable {

    private final String nullStr =  "UnHandled User Type";


    @FXML
    private Label sirenLabel;

    @FXML
    private Label incubatorNameLabel;

    @FXML
    private Label companyConcreteType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialise when FXML is loaded
    }

    public void setDetailedInfo(Borrower user){
        if(user instanceof Startup){
            // handle Teacher
            Startup company = (Startup) user;
            sirenLabel.setText(company.getSIREN());
            companyConcreteType.setText("Startup");
            incubatorNameLabel.setText(company.getIncubatorName());
        } else {
            sirenLabel.setText(nullStr);
            companyConcreteType.setText(nullStr);
            incubatorNameLabel.setText(nullStr);
        }
    }
}
