package inventory_app.view.tabs.inventory.detailedView.utils;

import inventory_app.view.tabs.inventory.filter.utils.FilterControllerLoader;
import javafx.fxml.FXMLLoader;

import java.net.URL;

public class EquipmentDetailedController{
    protected enum EQUIPMENT_DETAILED_CLASS_FXML_LOCATION{
        DEPTH_SENSOR(inventory_app.model.inventory.equipements.DepthSensor.class,FilterControllerLoader.class.getClassLoader().getResource("inventory_app/view/tabs/inventory/detailedView/depthSensor/depthSensorDetailedView.fxml")),
        TABLET(inventory_app.model.inventory.equipements.Tablet.class,FilterControllerLoader.class.getClassLoader().getResource("inventory_app/view/tabs/inventory/detailedView/tablet/tabletDetailedView.fxml")),
        SMARTPHONE(inventory_app.model.inventory.equipements.Smartphone.class,FilterControllerLoader.class.getClassLoader().getResource("inventory_app/view/tabs/inventory/detailedView/smartphone/smartphoneDetailedView.fxml")),
        VRHEADSET(inventory_app.model.inventory.equipements.VRHeadset.class,FilterControllerLoader.class.getClassLoader().getResource("inventory_app/view/tabs/inventory/detailedView/vrheadset/vrheadsetDetailedView.fxml"));

        private Class type;
        private URL location;

        EQUIPMENT_DETAILED_CLASS_FXML_LOCATION(Class type, URL location) {
            this.type = type;
            this.location = location;
        }

        public Class getType() {
            return type;
        }

        public URL getLocation() {
            return location;
        }
    }


    public FXMLLoader loadFrom(Class type){
        FXMLLoader fxmlLoader = null;
        for(EQUIPMENT_DETAILED_CLASS_FXML_LOCATION class_fxml_location : EQUIPMENT_DETAILED_CLASS_FXML_LOCATION.values()){
            if(class_fxml_location.getType().equals(type)){
                fxmlLoader = new FXMLLoader(class_fxml_location.getLocation());
            }
        }
        return fxmlLoader;
    }
}
