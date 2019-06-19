package inventory_app.view.tabs.user.detailedView;

import inventory_app.model.inventory.Borrower;
import inventory_app.model.inventory.Startup;
import inventory_app.model.users.People;
import inventory_app.model.users.Student;
import inventory_app.model.users.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PeopleListViewDetailedController implements Initializable {

    private final String nullStr =  "UnHandled User Type";

    private boolean modifyingStatus;

    private People selectedPeople;

    private People modifyPeopleContext;


    @FXML
    private Label peopleConcreteType;

    @FXML
    private TextField firstNameLabel;

    @FXML
    private TextField surnameLabel;

    @FXML
    private TextField addressLabel;

    @FXML
    private TextField phoneNumberLabel;

    @FXML
    private TextField emailLabel;

    @FXML
    private TextField gradeLabel;

    @FXML
    private Button validateButton;

    @FXML
    private Button modifyButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialise when FXML is loaded
        setEditableStatus(false);
        modifyingStatus = false;
        validateButton.setVisible(false);
    }

    private void setEditableStatus(boolean editableStatus){
        firstNameLabel.setEditable(editableStatus);
        surnameLabel.setEditable(editableStatus);
        addressLabel.setEditable(editableStatus);
        phoneNumberLabel.setEditable(editableStatus);
        emailLabel.setEditable(editableStatus);
        gradeLabel.setEditable(editableStatus);
    }

    public void setDetailedInfo(Borrower user){
        this.selectedPeople = (People) user;
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

    public void modifyFirstName(){
    }

    public void modifySurname(){
        if(surnameLabel.isEditable())
            this.modifyPeopleContext.setSurname(this.firstNameLabel.getText());
    }

    public void modifiyAddress(){
        if(addressLabel.isEditable())
            this.modifyPeopleContext.setAddress(this.addressLabel.getText());
    }

    public void modifyPhoneNumber(){
        if(phoneNumberLabel.isEditable())
            this.modifyPeopleContext.setPhoneNumber(this.phoneNumberLabel.getText());
    }

    public void modifyEmail(){
        if(this.emailLabel.isEditable()){
            this.modifyPeopleContext.setEmail(this.emailLabel.getText());
        }
    }

    public void modifyGrade(){

    }

    private  <E extends People> People  instanciatePeopleModifyingContext(E selectedPeople){
        if(Teacher.class.isInstance(selectedPeople)){
            return new Teacher((Teacher) selectedPeople);
        }else if(Student.class.isInstance(selectedPeople)){
            return new Student((Student)selectedPeople);
        }
        return null;
    }

    public void modify(){
        if(!modifyingStatus){
            validateButton.setVisible(true);
            modifyingStatus = true;
            setEditableStatus(true);
            modifyPeopleContext = instanciatePeopleModifyingContext(selectedPeople);
        } else {
            validateButton.setVisible(false);
            modifyingStatus = false;
            setEditableStatus(false);
            modifyPeopleContext = null;
        }
    }

    public void validate(){
        if(modifyPeopleContext != null){
            selectedPeople.setEmail(modifyPeopleContext.getEmail());
            selectedPeople.setPhoneNumber(modifyPeopleContext.getPhoneNumber());
            selectedPeople.setAddress(modifyPeopleContext.getAddress());
            selectedPeople.setSurname(modifyPeopleContext.getSurname());
            validateButton.setVisible(false);
        }
    }
}
