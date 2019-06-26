package inventory_app.view.tabs.user.addView;

import inventory_app.Main;
import inventory_app.model.users.Student;
import inventory_app.model.users.Teacher;
import inventory_app.model.users.Users;
import inventory_app.view.TextFieldValidator;
import inventory_app.view.tabs.user.UserListViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPeopleViewController implements Initializable {

    @FXML
    private Label typeLabel;

    @FXML
    private TextField SSNField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emailField;

    @FXML
    private Label gradeLabel;

    @FXML
    private ComboBox<Student.Grade> gradeComboBox;

    @FXML
    private Button createUserButton;

    @FXML
    private boolean validateSSN() {
        return TextFieldValidator.validate(SSNField, TextFieldValidator.FieldREGEX.SOCIAL_SECURITY_NUMBER_FR_REGEX);
    }

    @FXML
    private boolean validateFirstName() {
        return TextFieldValidator.validate(firstNameField, TextFieldValidator.FieldREGEX.NAME_REGEX);
    }

    @FXML
    private boolean validateLastName() {
        return TextFieldValidator.validate(lastNameField, TextFieldValidator.FieldREGEX.NAME_REGEX);
    }

    @FXML
    private boolean validateAddress() {
        return TextFieldValidator.validate(addressField, TextFieldValidator.FieldREGEX.ANY);
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
    private boolean validateAllFields() {
        return validateSSN() &&
                validateFirstName() &&
                validateLastName() &&
                validateAddress() &&
                validateEmail() &&
                validatePhoneNumber();
    }

    @FXML
    private void createUserButtonClicked() {
        if (validateAllFields()) {
            Users users = Main.contextContainer.getUsers();

            // A visible grade combobox means we're adding a student, else it's a teacher
            if (gradeComboBox.isVisible()) {
                Student student = new Student(SSNField.getText().trim(),
                        firstNameField.getText().trim(),
                        lastNameField.getText().trim(),
                        addressField.getText().trim(),
                        phoneNumberField.getText().trim(),
                        emailField.getText().trim(),
                        gradeComboBox.getValue());
                users.addUser(student);
                UserListViewController.addUser(student);
            } else {
                Teacher teacher= new Teacher(SSNField.getText().trim(),
                        firstNameField.getText().trim(),
                        lastNameField.getText().trim(),
                        addressField.getText().trim(),
                        phoneNumberField.getText().trim(),
                        emailField.getText().trim());
                users.addUser(teacher);
                UserListViewController.addUser(teacher);
            }
            ((Stage)createUserButton.getScene().getWindow()).close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Add User");
            alert.setContentText("Incorrect field(s)");

            // Trick to force the alert to always be displayed on top (sometimes, it popped behind the current window)
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);
            stage.toFront();
            stage.showAndWait();
        }
    }

    public void setTypeLabelText(String text) {
        typeLabel.setText(text);
    }

    public void hideGrade() {
        gradeLabel.setVisible(false);
        gradeComboBox.setVisible(false);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateGradeCombo();
    }
}
