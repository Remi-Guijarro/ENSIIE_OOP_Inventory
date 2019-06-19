package inventory_app.model.inventory.equipements;

import inventory_app.model.inventory.Equipment;
import inventory_app.model.inventory.Institute;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class Tablet extends Equipment implements Serializable {
    public enum OS { WINDOWS, MAC_OS, LINUX }

    private OS os;
    private int[] resolution;

    public Tablet(String name, String brand, Institute owner,
                  Date purchaseDate, double purchasePrice, OS os,
                  int[] screenResolution) throws IllegalArgumentException {
        super(name, brand, owner, purchaseDate, purchasePrice);
        this.os = os;
        setResolution(screenResolution);
    }

    public Tablet(String name, String brand, Institute owner,
                  Date purchaseDate, double purchasePrice,
                  Condition condition,
                  int[] screenResolution) throws IllegalArgumentException {
        super(name, brand, owner, purchaseDate, purchasePrice, condition);
        setResolution(screenResolution);
    }

    private void setResolution(int[] resolution) throws IllegalArgumentException {
        if (resolution.length != 2) {
            throw new IllegalArgumentException("Resolution array must be size 2.");
        }
        this.resolution = resolution;
    }

    @Override
    public String toString() {
        return "Webcam{" +
                "resolution=" + Arrays.toString(resolution) +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(o==this) return false;
        if(o==null) return false;
        if(!(o instanceof Tablet)) return false;
        else {
            Tablet t = (Tablet) o;
            return t.resolution.equals(this.resolution) && t.os == this.os;
        }
    }
}
