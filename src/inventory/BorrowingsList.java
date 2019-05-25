package inventory;

import java.util.ArrayList;
import java.util.List;

public class BorrowingsList {
    private ArrayList<Borrowing> borrowings = new ArrayList<>();

    private static BorrowingsList ourInstance = new BorrowingsList();

    public static BorrowingsList getInstance() {
        return ourInstance;
    }
    //////////////////////////////////////////////////////////////////

    private BorrowingsList() {
    }

    public ArrayList<Borrowable> getBorrowedItems() {
        ArrayList<Borrowable> borrowables = new ArrayList<>();
        for (Borrowing borrowing : borrowings) {
            borrowables.add(borrowing.getBorrowable());
        }
        return borrowables;
    }

    public boolean isBorrowed(Borrowable item) {
        //TODO
        return false;
    }

    public void addBorrowedItem(Borrowable item) {
        //TODO
    }

    public void removeBorrowedItem(Borrowable item) {
        //TODO
    }

    public ArrayList<Borrowing> getBorrowings() {
        return borrowings;
    }

    public ArrayList<Borrowing> getBorrowings(String reason) {
        //TODO
        return null;
    }

    public ArrayList<Borrowing> getBorrowings(Borrower borrower) {
        //TODO
        return null;
    }

    public ArrayList<Borrowing> getLateBorrowings() {
        //TODO
        return null;
    }
}
