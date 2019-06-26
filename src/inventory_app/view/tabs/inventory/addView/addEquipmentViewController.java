package inventory_app.view.tabs.inventory.addView;

import inventory_app.model.inventory.equipements.Smartphone;
import inventory_app.model.inventory.equipements.Tablet;
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

public class addEquipmentViewController implements Initializable {
    private Stage addEquipmentStage;

    @FXML
    AnchorPane root;

    @FXML
    private ComboBox<Class> equipmentComboBox;

    @FXML
    private AnchorPane equipmentForm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addEquipmentStage = new Stage();
        addEquipmentStage.initModality(Modality.APPLICATION_MODAL);
        addEquipmentStage.initStyle(StageStyle.DECORATED);
        addEquipmentStage.setTitle("Add Equipment");
        Scene scene = new Scene(root);
        addEquipmentStage.setScene(scene);
        addEquipmentStage.setAlwaysOnTop(true);
        addEquipmentStage.setResizable(false);
        addEquipmentStage.show();


        populateEquipmentComboBox();
        displayNamesInCombo();
        equipmentComboBox.setValue(Smartphone.class);
        setComboListeners();
        displayForm();
    }

    private void setComboListeners() {
        equipmentComboBox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            displayForm();
        });
    }

    private void populateEquipmentComboBox() {
        equipmentComboBox.getItems().addAll(Tablet.class, Smartphone.class);
    }

    private void displayNamesInCombo() {
        equipmentComboBox.setConverter(new StringConverter<Class>() {

            @Override
            public String toString(Class object) {
                return object.getSimpleName();
            }

            @Override
            public Class fromString(String string) {
                return equipmentComboBox.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    public void displayForm() {
        equipmentForm.getChildren().clear();
        switch (equipmentComboBox.getSelectionModel().getSelectedItem().getSimpleName()) {
            case "DepthSensor":
                displayTabletForm();
                break;
            case "Smartphone":
                displaySmartphoneForm();
                break;
            case "Tablet":
                displayTabletForm();
                break;
            case "VRHeadset":
                displayVRHeadsetForm();
                break;
        }
    }

    private void displayVRHeadsetForm() {
        //TODO
    }

    private void displayDepthSensorForm() {
        //TODO
    }

    private void displaySmartphoneForm() {
        try {
            FXMLLoader loader = loadForm("addSmartphoneView.fxml");
            //AddSmartphoneViewController controller = (AddSmartphoneViewController) loader.getController();
            //controller.setTypeLabelText("Add Teacher");
            //controller.hideGrade();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayTabletForm() {
        try {
            FXMLLoader loader = loadForm("addTabletView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FXMLLoader loadForm(String location) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(location));
        Parent root =  loader.load();
        equipmentForm.getChildren().add(root);
        return loader;
    }

    public void close(){
        addEquipmentStage.close();
    }
}
