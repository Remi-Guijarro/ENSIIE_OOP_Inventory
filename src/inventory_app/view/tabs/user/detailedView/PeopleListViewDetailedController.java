package inventory_app.view.tabs.user.detailedView;

import inventory_app.model.inventory.Borrower;
import inventory_app.model.users.People;
import inventory_app.model.users.Student;
import inventory_app.model.users.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PeopleListViewDetailedController implements Initializable {

    private final String nullStr =  "UnHandled User Type";

    private boolean isUpdating;

    private People selectedPeople;

    private People modifyPeopleContext;

    @FXML
    private Label peopleConcreteType;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField gradeField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button editButton;
    private ListView<Borrower> borrowerListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFieldsEditable(false);
        isUpdating = false;
        confirmButton.setVisible(false);
    }

    private void setFieldsEditable(boolean editableStatus){
        firstNameField.setEditable(editableStatus);
        surnameField.setEditable(editableStatus);
        addressField.setEditable(editableStatus);
        phoneNumberField.setEditable(editableStatus);
        emailField.setEditable(editableStatus);
        gradeField.setEditable(editableStatus);
    }

    public void setDetailedInfo(Borrower user, ListView<Borrower> borrowerListView){
        this.borrowerListView = borrowerListView;
        this.selectedPeople = (People) user;
        if(user instanceof Teacher){
            // handle Teacher
            Teacher teacher = (Teacher) user;
            peopleConcreteType.setText("Teacher");
            firstNameField.setText(teacher.getFirstName());
            surnameField.setText(teacher.getSurname());
            addressField.setText(teacher.getAddress());
            phoneNumberField.setText(teacher.getPhoneNumber());
            emailField.setText(teacher.getEmail());
            gradeField.setDisable(true);
        } else if (user instanceof Student){
            // handle Student
            Student student = (Student) user;
            peopleConcreteType.setText("Student");
            firstNameField.setText(student.getFirstName());
            surnameField.setText(student.getSurname());
            addressField.setText(student.getAddress());
            phoneNumberField.setText(student.getPhoneNumber());
            emailField.setText(student.getEmail());
            gradeField.setText(student.getGrade().toString());
        } else {
            peopleConcreteType.setText("Unhandled type");
            firstNameField.setText(nullStr);
            surnameField.setText(nullStr);
            addressField.setText(nullStr);
            phoneNumberField.setText(nullStr);
            emailField.setText(nullStr);
            gradeField.setText(nullStr);
        }
    }

    public void updateFirstName(){
        if(firstNameField.isEditable())
            this.modifyPeopleContext.setFirstName(this.firstNameField.getText());
    }

    public void updateSurname(){
        if(surnameField.isEditable())
            this.modifyPeopleContext.setSurname(this.surnameField.getText());
    }

    public void updateAddress(){
        if(addressField.isEditable())
            this.modifyPeopleContext.setAddress(this.addressField.getText());
    }

    public void updatePhoneNumber(){
        if(phoneNumberField.isEditable())
            this.modifyPeopleContext.setPhoneNumber(this.phoneNumberField.getText());
    }

    public void updateEmail(){
        if(this.emailField.isEditable()){
            this.modifyPeopleContext.setEmail(this.emailField.getText());
        }
    }

    public void updateGrade(){

    }

    private  <E extends People> People instantiatePeopleModifyingContext(E selectedPeople){
        if(Teacher.class.isInstance(selectedPeople)){
            return new Teacher((Teacher) selectedPeople);
        }else if(Student.class.isInstance(selectedPeople)){
            return new Student((Student)selectedPeople);
        }
        return null;
    }

    public void update(){
        if(!isUpdating){
            confirmButton.setVisible(true);
            isUpdating = true;
            setFieldsEditable(true);
            modifyPeopleContext = instantiatePeopleModifyingContext(selectedPeople);
        } else {
            confirmButton.setVisible(false);
            isUpdating = false;
            setFieldsEditable(false);
            modifyPeopleContext = null;
        }
    }

    private ArrayList<String> CheckInput(People people){
        return new ArrayList<>();
    }

    public void confirm(){
        if(modifyPeopleContext != null){
            System.out.println(modifyPeopleContext.getFirstName());
            selectedPeople.setFirstName(modifyPeopleContext.getFirstName());
            selectedPeople.setEmail(modifyPeopleContext.getEmail());
            selectedPeople.setPhoneNumber(modifyPeopleContext.getPhoneNumber());
            selectedPeople.setAddress(modifyPeopleContext.getAddress());
            selectedPeople.setSurname(modifyPeopleContext.getSurname());
            confirmButton.setVisible(false);
            borrowerListView.refresh();
        }
    }
}
