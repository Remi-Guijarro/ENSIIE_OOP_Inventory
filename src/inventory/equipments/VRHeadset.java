package inventory.equipments;

import inventory.Equipment;
import inventory.Institute;

import java.util.Date;

public class VRHeadset extends Equipment {
    private VRControllerPair controllers;

    public VRHeadset(String name, String brand, Institute owner,
                     Date purchaseDate, double purchasePrice)
            throws IllegalArgumentException {
        super(name, brand, owner, purchaseDate, purchasePrice);
        controllers = new VRControllerPair();
    }

    public VRHeadset(String name, String brand, Institute owner,
                     Date purchaseDate, double purchasePrice,
                     Condition condition)
            throws IllegalArgumentException {
        super(name, brand, owner, purchaseDate, purchasePrice, condition);
        controllers = new VRControllerPair();
    }
}
