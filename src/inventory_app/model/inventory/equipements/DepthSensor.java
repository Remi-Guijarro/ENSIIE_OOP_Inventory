package inventory_app.model.inventory.equipements;

import inventory_app.model.inventory.Equipment;
import inventory_app.model.inventory.Institute;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Geoffrey Delval
 */
public class DepthSensor extends Equipment implements Serializable {
    public DepthSensor(String name, String brand, Institute owner, Date purchaseDate, double purchasePrice) throws IllegalArgumentException {
        super(name, brand, owner, purchaseDate, purchasePrice);
    }

    public DepthSensor(String name, String brand, Institute owner, Date purchaseDate, double purchasePrice, Condition condition) throws IllegalArgumentException {
        super(name, brand, owner, purchaseDate, purchasePrice, condition);
    }
}
