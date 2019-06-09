package model.inventory.equipements;

import java.io.Serializable;

public class VRControllerPair implements Serializable {
    private String idLeftController;
    private String idRightController;
    private static int lastId = 0;

    public VRControllerPair() {
        idLeftController = "left_" +  (++lastId);
        idRightController = "right_" + lastId;
    }

    public String getIdLeftController() {
        return idLeftController;
    }

    public String getIdRightController() {
        return idRightController;
    }
}
