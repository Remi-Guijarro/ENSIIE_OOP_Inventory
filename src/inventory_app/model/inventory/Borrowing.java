package inventory_app.model.inventory;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Borrowing implements Serializable {
    private Borrowable borrowable;
    private Date borrowDate;
    private String borrowReason;
    private Borrower borrower;

    public Borrowing(Borrowable borrowable, Date borrowDate, String borrowReason, Borrower borrower) {
        this.borrowable = borrowable;
        this.borrowDate = borrowDate;
        this.borrowReason = borrowReason;
        this.borrower = borrower;
    }

    public enum BorrowReasonType{
        LESSON,OTHER;
    }

    public class BorrowReason{
        private BorrowReasonType type;
        private String message;

        public BorrowReason(BorrowReasonType type, String message) {
            this.type = type;
            this.message = message;
        }

        public BorrowReasonType getType() {
            return type;
        }

        public void setType(BorrowReasonType type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
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
        Calendar cal = Calendar.getInstance();
        cal.setTime(borrowDate);
        cal.add(Calendar.DAY_OF_WEEK, 14);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DAY_OF_WEEK, 2);
        } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }
        return cal.getTime();
    }

    public String getBorrowReason() {
        return borrowReason;
    }

    public void setBorrowReason(String borrowReason) {
        this.borrowReason = borrowReason;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public boolean isLate() {
        return Calendar.getInstance().getTime().after(getReturnDate());
    }
}
