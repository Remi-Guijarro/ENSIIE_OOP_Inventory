package inventory_app.model.inventory;

import java.io.Serializable;
import java.util.Date;

public interface Borrowable extends Serializable {
    /**
     * Set the current Borrowable as Borrowed with the given options
     * @param borrowDate
     * @param reason
     * @param borrower
     */
    void setBorrowed(Date borrowDate, String reason, Borrower borrower);

    /**
     * set the current Borrowable as Return
     */
    void setReturned();
}
