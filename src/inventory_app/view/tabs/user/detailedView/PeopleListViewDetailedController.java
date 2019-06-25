package inventory_app.view.tabs.user.detailedView;

import inventory_app.model.inventory.Borrower;
import inventory_app.model.users.People;
import inventory_app.model.users.Student;
import inventory_app.model.users.Teacher;
import inventory_app.view.TextFieldValidator;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class PeopleListViewDetailedController implements Initializable {

    private final String nullStr =  "UnHandled User Type";

    private boolean isUpdating;

    private People selectedPeople;

    //private People modifyPeopleContext;

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
    private ComboBox<Student.Grade> gradeComboBox;

    @FXML
    private Button confirmButton;

    @FXML
    private Button editButton;

    @FXML
    private Label editResultLabel;

    private HashMap<Node, String> fieldProxy;

    private ListView<Borrower> borrowerListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isUpdating = false;
        confirmButton.setVisible(false);
        editResultLabel.setVisible(false);
        fieldProxy = new HashMap<>();
    }

    private void populateGradeCombo() {
        gradeComboBox.getItems().addAll(Student.Grade.values());
        displayGradeNamesInCombo();
    }

    private void displayGradeNamesInCombo() {
        gradeComboBox.setConverter(new StringConverter<Student.Grade>() {
            @Override
            public String toString(Student.Grade grade) {
                String gradeString = grade.toString();
                if (gradeString.charAt(0) == '_')
                    gradeString = gradeString.substring(1);

                return gradeString;
            }

            @Override
            public Student.Grade fromString(String string) {
                return gradeComboBox.getItems().stream().filter(ap ->
                        ap.toString().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void setFieldsDisable(boolean value){
        firstNameField.setDisable(value);
        surnameField.setDisable(value);
        addressField.setDisable(value);
        phoneNumberField.setDisable(value);
        emailField.setDisable(value);
        if (selectedPeople instanceof Student)
            gradeComboBox.setDisable(value);

    }

    public void setDetailedInfo(Borrower user, ListView<Borrower> borrowerListView){
        this.borrowerListView = borrowerListView;
        this.selectedPeople = (People) user;
        setFieldsDisable(true);
        if(user instanceof Teacher){
            // handle Teacher
            Teacher teacher = (Teacher) user;
            peopleConcreteType.setText("Teacher");
            firstNameField.setText(teacher.getFirstName());
            surnameField.setText(teacher.getSurname());
            addressField.setText(teacher.getAddress());
            phoneNumberField.setText(teacher.getPhoneNumber());
            emailField.setText(teacher.getEmail());
            gradeComboBox.setDisable(true);
        } else if (user instanceof Student){
            // handle Student
            Student student = (Student) user;
            peopleConcreteType.setText("Student");
            firstNameField.setText(student.getFirstName());
            surnameField.setText(student.getSurname());
            addressField.setText(student.getAddress());
            phoneNumberField.setText(student.getPhoneNumber());
            emailField.setText(student.getEmail());
            gradeComboBox.setValue(student.getGrade());
            populateGradeCombo();
        } else {
            peopleConcreteType.setText("Unhandled type");
            setFieldsDisable(true);
            gradeComboBox.setDisable(true);
        }
    }

    @FXML
    private boolean validateFirstName() {
        return TextFieldValidator.validate(firstNameField, TextFieldValidator.FieldREGEX.NAME_REGEX);
    }

    @FXML
    private boolean validateSurname() {
        return TextFieldValidator.validate(surnameField, TextFieldValidator.FieldREGEX.NAME_REGEX);
    }

    @FXML
    private boolean validatePhoneNumber() {
        return TextFieldValidator.validate(phoneNumberField, TextFieldValidator.FieldREGEX.PHONE_NUMBER_REGEX);
    }

    @FXML
    private boolean validateEmail() {
        return TextFieldValidator.validate(emailField, TextFieldValidator.FieldREGEX.EMAIL_REGEX);
    }

    @FXML
    private boolean validateAddress() {
        return TextFieldValidator.validate(addressField, TextFieldValidator.FieldREGEX.ANY);
    }

    private void updateUser() {
        updateFirstName();
        updateSurname();
        updatePhoneNumber();
        updateEmail();
        updateAddress();
        if (selectedPeople instanceof Student) {
            updateStudentGrade();
        }
    }

    private void updateFirstName(){
            selectedPeople.setFirstName(this.firstNameField.getText().trim());
    }

    private void updateSurname(){
        selectedPeople.setSurname(this.surnameField.getText().trim());
    }

    private void updateAddress(){
        selectedPeople.setAddress(this.addressField.getText().trim());
    }

    private void updatePhoneNumber(){
        selectedPeople.setPhoneNumber(this.phoneNumberField.getText().trim());
    }

    private void updateEmail(){
        selectedPeople.setEmail(this.emailField.getText().trim());
    }

    private void updateStudentGrade(){
        Student current = (Student) selectedPeople;
        current.setGrade(gradeComboBox.getValue());
    }

    /*private  <E extends People> People instantiatePeopleModifyingContext(E selectedPeople){
        if(selectedPeople instanceof Teacher){
            return new Teacher((Teacher) selectedPeople);
        }else if(selectedPeople instanceof Student){
            return new Student((Student)selectedPeople);
        }
        return null;
    }*/

    private boolean validateAllFields() {
        // We want to execute ALL the functions, that's why we use &
        return (validateFirstName() &
                validateSurname() &
                validatePhoneNumber() &
                validateEmail() &
                validateAddress()
        );
    }

    public void editClicked(){
        if(!isUpdating){
            isUpdating = true;

            validateAllFields();

            setFieldsDisable(false);
            confirmButton.setVisible(true);
            //selectedPeople = instantiatePeopleModifyingContext(selectedPeople);

            editButton.setText("Cancel");
            populateProxy();
        } else {
            cancelFieldsText();
            stopUpdating();
        }
    }

    private void stopUpdating() {
        isUpdating = false;

        removeFieldsValidationColor();

        setFieldsDisable(true);
        confirmButton.setVisible(false);

        //modifyPeopleContext = null;

        editButton.setText("Edit...");
        fieldProxy.clear();
    }

    private void removeFieldsValidationColor() {
        TextFieldValidator.setBlankBackground(firstNameField);
        TextFieldValidator.setBlankBackground(surnameField);
        TextFieldValidator.setBlankBackground(addressField);
        TextFieldValidator.setBlankBackground(emailField);
        TextFieldValidator.setBlankBackground(phoneNumberField);
    }


    private void populateProxy() {
        fieldProxy.put(firstNameField, firstNameField.getText());
        fieldProxy.put(surnameField, surnameField.getText());
        fieldProxy.put(addressField, addressField.getText());
        fieldProxy.put(phoneNumberField, phoneNumberField.getText());
        fieldProxy.put(emailField, emailField.getText());
        if (selectedPeople instanceof Student)
            fieldProxy.put(gradeComboBox, gradeComboBox.getValue().toString());
    }

    private void cancelFieldsText() {
        firstNameField.setText(fieldProxy.get(firstNameField));
        surnameField.setText(fieldProxy.get(surnameField));
        addressField.setText(fieldProxy.get(addressField));
        emailField.setText(fieldProxy.get(emailField));
        phoneNumberField.setText(fieldProxy.get(phoneNumberField));
        if (selectedPeople instanceof Student)
            gradeComboBox.setValue(Student.Grade.valueOf(fieldProxy.get(gradeComboBox)));
    }

    private ArrayList<String> CheckInput(People people){
        return new ArrayList<>();
    }

    public void confirm(){
        if (validateAllFields()) {
            updateUser();
            confirmButton.setVisible(false);
            borrowerListView.refresh();
            displayDatabaseUpdated(3000);
            stopUpdating();
        } else {
            displayEditError(3000);
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
}
