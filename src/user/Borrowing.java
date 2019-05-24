package user;

import java.util.Date;

public class Borrowing {
    private Date borrowDate;
    private Date returnDate;
    private String borrowReason;

    public Borrowing(Date borrowDate, Date returnDate, String borrowReason) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.borrowReason = borrowReason;
    }
}
