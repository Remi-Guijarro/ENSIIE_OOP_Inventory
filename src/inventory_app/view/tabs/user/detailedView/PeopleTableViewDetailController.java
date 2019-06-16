package inventory_app.view.tabs.user.detailedView;

import inventory_app.model.inventory.Borrower;
import inventory_app.model.inventory.Startup;
import inventory_app.model.users.People;
import inventory_app.model.users.Student;
import inventory_app.model.users.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PeopleTableViewDetailController implements Initializable {

    private final String nullStr =  "UnHandled User Type";

    @FXML
    private Label peopleConcreteType;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label gradeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialise when FXML is loaded
    }

    public void setDetailedInfo(Borrower user){
        if(user instanceof Teacher){
            // handle Teacher
            Teacher teacher = (Teacher) user;
            peopleConcreteType.setText("Teacher");
            firstNameLabel.setText(teacher.getFirstName());
            surnameLabel.setText(teacher.getSurname());
            addressLabel.setText(teacher.getAddress());
            phoneNumberLabel.setText(teacher.getPhoneNumber());
            emailLabel.setText(teacher.getEmail());
            gradeLabel.setText("N/ A");
        } else if (user instanceof Student){
            // handle Student
            Student student = (Student) user;
            peopleConcreteType.setText("Student");
            firstNameLabel.setText(student.getFirstName());
            surnameLabel.setText(student.getSurname());
            addressLabel.setText(student.getAddress());
            phoneNumberLabel.setText(student.getPhoneNumber());
            emailLabel.setText(student.getEmail());
            gradeLabel.setText(student.getGrade().toString());
        } else {
            peopleConcreteType.setText("Unhandled type");
            firstNameLabel.setText(nullStr);
            surnameLabel.setText(nullStr);
            addressLabel.setText(nullStr);
            phoneNumberLabel.setText(nullStr);
            emailLabel.setText(nullStr);
            gradeLabel.setText(nullStr);
        }
    }
}
