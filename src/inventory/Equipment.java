package inventory;

import user.Borrower;

import java.util.Date;
import java.util.Map;

public abstract class Equipment {


    public enum Condition {
        GOOD,
        USED,
        BROKEN;
    }
    private Institute owner;

    private final String reference;
    private Map<String, Integer> lastId;
    private String name;
    private String brand;
    private Date purchaseDate;
    private double purchasePrice;
    private Condition condition = Condition.GOOD;
    private Borrower borrower = null;
    private boolean isBorrowed = false;
    private Stock inventory;
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

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
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

    public Stock getInventory() {
        return inventory;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (!(o instanceof Equipment)) return false;
        else {
            Equipment e = (Equipment) o;
            if (!e.getReference().equals(this.getReference())) return false;
            if (!e.getBrand().equals(this.getBrand())) return false;
            if (!e.getName().equals(this.getName())) return false;
            if (!e.getPurchaseDate().equals(this.getPurchaseDate())) return false;
            if (e.getPurchasePrice() != this.getPurchasePrice()) return false;
            if (!e.getCondition().equals(this.getCondition())) return false;
            if (e.isBorrowed() != this.isBorrowed()) return false;
            if (!e.getInventory().equals(this.getInventory())) return false;

            return true;
        }
    }
}
