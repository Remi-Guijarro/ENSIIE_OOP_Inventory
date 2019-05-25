package inventory;

import java.util.Date;

public class Borrowing {
    private Equipment equipment;
    private Date borrowDate;
    private Date returnDate;
    private String borrowReason;

    public Borrowing(Equipment equipment, Date borrowDate, Date returnDate, String borrowReason) {
        this.equipment = equipment;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.borrowReason = borrowReason;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getBorrowReason() {
        return borrowReason;
    }

    public void setBorrowReason(String borrowReason) {
        this.borrowReason = borrowReason;
    }
}
