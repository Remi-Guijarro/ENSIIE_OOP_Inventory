package inventory_app.model.inventory.equipements;

import inventory_app.model.inventory.Equipment;
import inventory_app.model.inventory.Institute;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents a smartphone.
 *
 * @author Geoffrey Delval
 */

public class Smartphone extends Equipment implements Serializable {
    /**
     * Existing operating systems
     */
    public enum PHONE_OS { ANDROID, IOS, WINDOWS_PHONE }

    /**
     * Phone's operating system
     */
    private PHONE_OS PHONE_OS;
    /**
     * Phone's screen size in inches
     */
    private double screenSize;

    public Smartphone(String name, String brand, Institute owner,
                      Date purchaseDate, double purchasePrice,
                      PHONE_OS PHONE_OS, double screenSize)
            throws IllegalArgumentException {
        super(name, brand, owner, purchaseDate, purchasePrice);
        this.PHONE_OS = PHONE_OS;
        this.screenSize = screenSize;
    }

    public Smartphone(String name, String brand, Institute owner,
                      Date purchaseDate, double purchasePrice,
                      Condition condition,
                      PHONE_OS PHONE_OS, double screenSize)
            throws IllegalArgumentException {
        super(name, brand, owner, purchaseDate, purchasePrice, condition);
        this.PHONE_OS = PHONE_OS;
        this.screenSize = screenSize;
    }

    /**
     * @return {@link PHONE_OS } return the OS of the phone
     */
    public PHONE_OS getPHONE_OS() {
        return PHONE_OS;
    }

    public double getScreenSize() {
        return screenSize;
    }

    @Override
    public String toString() {
        return "Smartphone{" +
                "PHONE_OS=" + PHONE_OS +
                ", screenSize=" + screenSize +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (!(o instanceof Smartphone)) return false;
        else {
            Smartphone p = (Smartphone) o;
            return  p.getReference().equalsIgnoreCase(this.getReference())
                    && p.PHONE_OS == this.PHONE_OS &&
                    p.screenSize == this.screenSize;
        }
    }

}
