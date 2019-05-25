package inventory;

import java.util.Date;

public interface Borrowable {
    void setBorrowed(Date borrowDate, String reason, Borrower borrower);
    void setReturned();
}
