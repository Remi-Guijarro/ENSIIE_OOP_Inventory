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

    public void setOwner(Institute owner) {
        this.owner = owner;
    }


    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getReference() {
        return reference;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public double getPurchasePrice() {
        return purchasePrice;
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
