package user;

import inventory.Equipment;
import inventory.Stock;

import java.util.Date;
import java.util.List;

public interface Borrower {

    public void borrow (Equipment equipment, Date borrowDate, Date returnDate, String reason);
    public List<Equipment> getBorrowedEquipement();
    public void returnEquipement(Equipment equipment);
    /*public void borrow (Stock<Equipment> equipments, Date borrowDate, Date returnDate, String reason);*/

}
