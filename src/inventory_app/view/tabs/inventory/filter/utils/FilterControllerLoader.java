package inventory_app.view.tabs.inventory.filter.utils;

import inventory_app.model.inventory.equipements.DepthSensor;
import inventory_app.model.inventory.equipements.Smartphone;
import inventory_app.model.inventory.equipements.Tablet;
import inventory_app.model.inventory.equipements.VRHeadset;
import javafx.fxml.FXMLLoader;

import java.net.URL;

public class FilterControllerLoader {// this.getClass().getClassLoader().getResource("inventory_app/view/tabs
    protected enum CLASS_FXML_LOCATION{
        DEPTH_SENSOR(inventory_app.model.inventory.equipements.DepthSensor.class,FilterControllerLoader.class.getClassLoader().getResource("inventory_app/view/tabs/inventory/filter/depthSensor/depthSensorFilterView.fxml")),
        TABLET(inventory_app.model.inventory.equipements.Tablet.class,FilterControllerLoader.class.getClassLoader().getResource("inventory_app/view/tabs/inventory/filter/tablet/tabletFilterView.fxml")),
        SMARTPHONE(inventory_app.model.inventory.equipements.Smartphone.class,FilterControllerLoader.class.getClassLoader().getResource("inventory_app/view/tabs/inventory/filter/smartphone/smartphoneFilterView.fxml")),
        VRHEADSET(inventory_app.model.inventory.equipements.VRHeadset.class,FilterControllerLoader.class.getClassLoader().getResource("inventory_app/view/tabs/inventory/filter/vrheadset/vrheadsetFilterView.fxml"));

        private Class type;
        private URL location;

        CLASS_FXML_LOCATION(Class type, URL location) {
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
        for(CLASS_FXML_LOCATION class_fxml_location : CLASS_FXML_LOCATION.values()){
            if(class_fxml_location.getType().equals(type)){
                fxmlLoader = new FXMLLoader(class_fxml_location.getLocation());
            }
        }
        return fxmlLoader;
    }
}
