package inventory.equipments;

import inventory.Equipment;
import inventory.Institute;

import java.util.Date;

public class DepthSensor extends Equipment {
    public DepthSensor(String name, String brand, Institute owner, Date purchaseDate, double purchasePrice) throws IllegalArgumentException {
        super(name, brand, owner, purchaseDate, purchasePrice);
    }

    public DepthSensor(String name, String brand, Institute owner, Date purchaseDate, double purchasePrice, Condition condition) throws IllegalArgumentException {
        super(name, brand, owner, purchaseDate, purchasePrice, condition);
    }
}
