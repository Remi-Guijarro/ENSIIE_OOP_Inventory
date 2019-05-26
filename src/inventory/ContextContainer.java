package inventory;

import java.io.Serializable;

public class ContextContainer implements Serializable {
    private BorrowingsList borrowingsList;
    private BorrowablesList borrowablesList;
    private BorrowersList borrowersList;

    public ContextContainer(ContextContainer contextContainer){
        this.borrowingsList = contextContainer.borrowingsList;
        this.borrowersList = contextContainer.borrowersList;
        this.borrowablesList = contextContainer.borrowablesList;
    }

    public ContextContainer() {
        borrowablesList = BorrowablesList.getInstance();
        borrowersList = BorrowersList.getInstance();
        borrowingsList = BorrowingsList.getInstance();
    }

    public BorrowingsList getBorrowingsList() {
        return borrowingsList;
    }

    public BorrowablesList getBorrowablesList() {
        return borrowablesList;
    }

    public BorrowersList getBorrowersList() {
        return borrowersList;
    }
}
