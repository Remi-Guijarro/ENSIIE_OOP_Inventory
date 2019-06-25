package inventory_app.view.tabs.user.addView;

import inventory_app.model.inventory.Startup;
import inventory_app.model.users.Student;
import inventory_app.model.users.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddBorrowerViewController implements Initializable {
    private Stage addBorrowerStage;

    @FXML
    AnchorPane root;

    @FXML
    private ComboBox<Class> borrowerComboBox;

    @FXML
    private AnchorPane userForm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addBorrowerStage = new Stage();
        addBorrowerStage.initModality(Modality.APPLICATION_MODAL);
        addBorrowerStage.initStyle(StageStyle.DECORATED);
        addBorrowerStage.setTitle("Add Borrower");
        Scene scene = new Scene(root);
        addBorrowerStage.setScene(scene);
        addBorrowerStage.setAlwaysOnTop(true);
        addBorrowerStage.setResizable(false);
        addBorrowerStage.show();


        populateBorrowerComboBox();
        displayNamesInCombo();
        borrowerComboBox.setValue(Student.class);
        setComboListeners();
        displayForm();
    }

    private void setComboListeners() {
        borrowerComboBox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            displayForm();
        });
    }

    private void populateBorrowerComboBox() {
        borrowerComboBox.getItems().addAll(Student.class, Teacher.class,  Startup.class);
    }

    private void displayNamesInCombo() {
        borrowerComboBox.setConverter(new StringConverter<Class>() {

            @Override
            public String toString(Class object) {
                return object.getSimpleName();
            }

            @Override
            public Class fromString(String string) {
                return borrowerComboBox.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    public void displayForm() {
        userForm.getChildren().clear();
        //TODO: Does not respect the Open-Closed principle, i guess we should refactor at some point
        switch (borrowerComboBox.getSelectionModel().getSelectedItem().getSimpleName()) {
            case "Student":
                displayStudentForm();
                break;
            case "Teacher":
                displayTeacherForm();
                break;
            case "Startup":
                displayStartupForm();
                break;
        }
    }

    private void displayStartupForm() {
        try {
            loadForm("addStartupView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayTeacherForm() {
        try {
            FXMLLoader loader = loadForm("addPeopleView.fxml");
            AddPeopleViewController controller = (AddPeopleViewController) loader.getController();
            controller.setTypeLabelText("Add Teacher");
            controller.hideGrade();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayStudentForm() {
        try {
            FXMLLoader loader = loadForm("addPeopleView.fxml");
            ((AddPeopleViewController) loader.getController()).setTypeLabelText("Add Student");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FXMLLoader loadForm(String location) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(location));
        Parent root =  loader.load();
        userForm.getChildren().add(root);
        return loader;
    }

    public void close(){
        addBorrowerStage.close();
    }
}
