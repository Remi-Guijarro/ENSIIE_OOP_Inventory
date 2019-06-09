package inventory_app.model.inventory;

import java.io.Serializable;
import java.util.Date;

public interface Borrowable extends Serializable {
    void setBorrowed(Date borrowDate, String reason, Borrower borrower);
    void setReturned();
}
