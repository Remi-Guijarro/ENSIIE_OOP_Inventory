package inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

public interface Borrower {
    void borrow(Equipment equipment,
                Date borrowDate, String reason)
            throws NoSuchElementException;

    void borrow(ArrayList<Equipment> equipment,
                Date borrowDate, String reason)
            throws NoSuchElementException;

    ArrayList<Equipment> getBorrowedEquipment();

    void returnEquipment(Equipment equipment)
        throws NoSuchElementException;

    void returnEquipments(ArrayList<Equipment> equipments)
        throws NoSuchElementException;

}
