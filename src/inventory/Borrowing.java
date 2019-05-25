package inventory;

import java.util.Date;

public class Borrowing {
    private Borrowable borrowable;
    private Date borrowDate;
    private Date returnDate;
    private String borrowReason;

    public Borrowing(Borrowable borrowable, Date borrowDate, Date returnDate, String borrowReason)
            throws IllegalArgumentException {
        if(borrowDate.compareTo(returnDate) > 0)
            throw new IllegalArgumentException("The return date cannot be before the borrow date");

        this.borrowable = borrowable;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.borrowReason = borrowReason;
    }

    public Borrowable getBorrowable() {
        return borrowable;
    }

    public void setBorrowable(Borrowable borrowable) {
        this.borrowable = borrowable;
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
