package inventory.equipments;

public class VRControllerPair {
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
