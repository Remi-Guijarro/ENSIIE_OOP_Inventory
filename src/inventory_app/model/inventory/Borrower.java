package inventory_app.model.inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;

public interface Borrower extends Serializable {

    String getName();

    /**
     * @param equipment
     * @param reason
     * @throws NoSuchElementException
     * Set the given Equipment as Borrowed by The current Borrower with the given reason
     */
    default void borrow(Equipment equipment, String reason) throws NoSuchElementException {
        if (!InventoryManager.getInstance().getAll().contains(equipment))
            throw new NoSuchElementException("The equipment to borrow does not exist.");
        equipment.setBorrowed(Calendar.getInstance().getTime(), reason, this);
    }

    /**
     * @param equipments
     * @param reason
     * @throws NoSuchElementException
     * Set the given Equipments as Borrowed by the current Borrower with the same given reason
     */
    default void borrow(ArrayList<Equipment> equipments, String reason) throws NoSuchElementException {
        for (Equipment e : equipments) {
            borrow(e, reason);
        }
    }

    /**
     * @return ArrayList<Equipment>
     *     the Borrowed Equipments
     */
    default ArrayList<Equipment> getBorrowedEquipment() {
        ArrayList<Borrowing> borrowings = BorrowingsList.getInstance().getBorrowings();
        ArrayList<Equipment> equipments = new ArrayList<>();
        for (Borrowing e : borrowings) {
            Borrowable borrowable = e.getBorrowable();
            if (e.getBorrower().equals(this) && borrowable instanceof Equipment) {
                equipments.add((Equipment) borrowable);
            }
        }
        return equipments;
    }

    /**
     * @param equipment
     * @throws NoSuchElementException
     * Set the current Equipment as returned
     */
    default void returnEquipment(Equipment equipment) throws NoSuchElementException {
        if (!InventoryManager.getInstance().getAll().contains(equipment))
            throw new NoSuchElementException("The equipment to borrow does not exist.");
        if (!getBorrowedEquipment().contains(equipment))
            throw new NoSuchElementException("This borrower did not borrow that equipment."); //TODO: use custom exception

        equipment.setReturned();
    }

    /**
     * @param equipments
     * @throws NoSuchElementException
     * Set the current Equipments as returned
     */
    default void returnEquipments(ArrayList<Equipment> equipments) throws NoSuchElementException {
        for (Equipment e : equipments) {
            returnEquipment(e);
        }
    }

}
