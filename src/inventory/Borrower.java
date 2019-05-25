package inventory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;

public interface Borrower {
    default void borrow(Equipment equipment, String reason) throws NoSuchElementException {
        if (!InventoryManager.getInstance().getAll().contains(equipment))
            throw new NoSuchElementException("The equipment to borrow does not exist.");
        //TODO: add custom exception if already borrowed

        equipment.setBorrowed(Calendar.getInstance().getTime(), reason, this);
    }

    default void borrow(ArrayList<Equipment> equipments, String reason) throws NoSuchElementException {
        for (Equipment e : equipments) {
            borrow(e, reason);
        }
    }

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

    default void returnEquipment(Equipment equipment) throws NoSuchElementException {
        if (!InventoryManager.getInstance().getAll().contains(equipment))
            throw new NoSuchElementException("The equipment to borrow does not exist.");
        if (!getBorrowedEquipment().contains(equipment))
            throw new NoSuchElementException("This borrower did not borrow that equipment."); //TODO: use custom exception

        equipment.setReturned();
    }

    default void returnEquipments(ArrayList<Equipment> equipments) throws NoSuchElementException {
        for (Equipment e : equipments) {
            returnEquipment(e);
        }
    }

}
