package inventory_app.model.inventory;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class Equipment implements Borrowable,Serializable {

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public enum Condition {
        GOOD,
        USED,
        BROKEN;

    }

    public enum Location {
        ROOM_266,
        ROOM_199,
        ROOM_301
    }

    private Institute owner;

    private final String reference;
    private static Map<String, Integer> lastId = new HashMap<>();
    private String name;
    private String brand;
    private Date purchaseDate;
    private double purchasePrice;
    private Condition condition = Condition.GOOD;
    private Location location;


    public Equipment(String name, String brand, Institute owner,
                     Date purchaseDate, double purchasePrice)
            throws IllegalArgumentException {
        if (purchasePrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.name = name;
        this.brand = brand;
        this.owner = owner;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        // Create the reference
        String key = getClass().getSimpleName();
        int lastIDNumber;
        if (!lastId.containsKey(key)) {
            lastIDNumber = 0;
            lastId.put(key, ++lastIDNumber);
        } else {
            lastIDNumber = lastId.get(getClass().getSimpleName());
        }
        reference = getClass().getSimpleName() + "_" + String.format("%02d", lastIDNumber);
        lastId.put(getClass().getSimpleName(), ++lastIDNumber);
    }
    public Equipment(String name, String brand, Institute owner,
                     Date purchaseDate, double purchasePrice,
                     Condition condition)
            throws IllegalArgumentException {
        this(name, brand, owner, purchaseDate, purchasePrice);
        this.condition = condition;
    }

    /**
     * @return Institute the institute that own the current Equipment
     */
    public Institute getOwner() {
        return owner;
    }

    /**
     * @param owner set the owner for the current equipement
     */
    public void setOwner(Institute owner) {
        this.owner = owner;
    }


    /**
     * @return The Condition of the current Equipement {@link Condition}
     */
    public Condition getCondition() {
        return condition;
    }


    /**
     * @param condition {@link Condition}
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
    }


    /**
     * @return String the reference of the current Equipment
     */
    public String getReference() {
        return reference;
    }


    /**
     * @return String the name of the current Equipment
     */
    public String getName() {
        return name;
    }


    /**
     * @return String the Brand of the current Equipment
     */
    public String getBrand() {
        return brand;
    }


    /**
     * @return Date, the purchase date of the current Equipement
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * @return double the price of the current Equipement
     */
    public double getPurchasePrice() {
        return purchasePrice;
    }

    public static Map<String, Integer> getLastId() {
        return lastId;
    }

    public static void setLastId(Map<String, Integer>lastId) {
        Equipment.lastId = lastId;
    }

    @Override
    public void setBorrowed(Date borrowDate, String reason, Borrower borrower) {
        BorrowingsList.getInstance().addBorrowedItem(this, borrowDate, reason, borrower);
    }

    @Override
    public void setReturned() {
        BorrowingsList.getInstance().removeBorrowedItem(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (!(o instanceof Equipment)) return false;
        else {
            Equipment e = (Equipment) o;

            return e.reference.equals(this.reference) &&
                    e.brand.equals(this.brand) &&
                    e.name.equals(this.name) &&
                    e.purchaseDate.equals(this.purchaseDate) &&
                    e.purchasePrice == this.purchasePrice &&
                    e.condition.equals(this.condition);
        }
    }
}
