package inventory_app.view.tabs.user.detailedView;

import inventory_app.model.inventory.Borrower;
import inventory_app.model.users.People;
import inventory_app.model.users.Student;
import inventory_app.model.users.Teacher;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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

    @FXML
    private Label editResultLabel;

    private ArrayList<TextField> fieldProxy;

    private ListView<Borrower> borrowerListView;

    private final String NAME_REGEX = "^([A-Za-z\\-\\ ]+)$";
    private final String EMAIL_REGEX = "^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)$";
    private final String PHONE_NUMBER_REGEX = "^0([0-9]{9})$";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFieldsEditable(false);
        isUpdating = false;
        confirmButton.setVisible(false);
        editResultLabel.setVisible(false);
        fieldProxy = new ArrayList<>();
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

    private boolean validateField(TextField field, final String regex) {
        if (field.getText().matches(regex)) {
            field.setStyle("-fx-background-color: #a5ff93");
            return true;
        } else {
            field.setStyle("-fx-background-color: #ff998c");
            return false;
        }
    }

    public boolean validateFirstName() {
        return validateField(firstNameField, NAME_REGEX);
    }

    public boolean validateSurname() {
        return validateField(surnameField, NAME_REGEX);
    }

    public boolean validatePhoneNumber() {
        return validateField(phoneNumberField, PHONE_NUMBER_REGEX);
    }

    public boolean validateEmail() {
        return validateField(emailField, EMAIL_REGEX);
    }

    public boolean validateAddress() {
        return validateField(addressField, ".*");    //any string is okay
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

    public void editClicked(){
        if(!isUpdating){

            validateFirstName();
            validateSurname();
            validatePhoneNumber();
            validateAddress();
            validateEmail();

            confirmButton.setVisible(true);
            isUpdating = true;
            setFieldsEditable(true);
            modifyPeopleContext = instantiatePeopleModifyingContext(selectedPeople);

            //fieldProxy.addAll(firstNameField, surnameField, addressField, phoneNumberField, emailField, gradeField);
            editButton.setText("Cancel");
        } else {
            confirmButton.setVisible(false);
            isUpdating = false;
            setFieldsEditable(false);
            modifyPeopleContext = null;
            editButton.setText("Edit...");
        }
    }

    private ArrayList<String> CheckInput(People people){
        return new ArrayList<>();
    }

    public void confirm(){
        if(modifyPeopleContext != null){
            if (validateAllField()) {
                selectedPeople.setFirstName(modifyPeopleContext.getFirstName());
                selectedPeople.setEmail(modifyPeopleContext.getEmail());
                selectedPeople.setPhoneNumber(modifyPeopleContext.getPhoneNumber());
                selectedPeople.setAddress(modifyPeopleContext.getAddress());
                selectedPeople.setSurname(modifyPeopleContext.getSurname());
                confirmButton.setVisible(false);
                borrowerListView.refresh();
                displayDatabaseUpdated(3000);
            } else {
                displayEditError(3000);
            }
        }
    }

    private void displayDatabaseUpdated(int ms) {
        editResultLabel.setText("Database updated");
        editResultLabel.setTextFill(Color.GREEN);
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(ms/1000)
        );
        visiblePause.setOnFinished(
                event -> editResultLabel.setVisible(false)
        );
        editResultLabel.setVisible(true);
        visiblePause.play();
    }

    private void displayEditError(int ms) {
        editResultLabel.setText("Incorrect field(s)");
        editResultLabel.setTextFill(Color.RED);
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(ms/1000)
        );
        visiblePause.setOnFinished(
                event -> editResultLabel.setVisible(false)
        );
        editResultLabel.setVisible(true);
        visiblePause.play();
    }

    private boolean validateAllField() {
        return (validateFirstName() &&
                validateSurname() &&
                validateAddress() &&
                validateEmail() &&
                validatePhoneNumber());
    }
}
