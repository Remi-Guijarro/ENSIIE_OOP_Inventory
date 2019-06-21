package inventory_app.model.inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

public class BorrowingsList implements Serializable {
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

    public Borrowing getBorrowerFrom(Borrowable b){
        Optional<Borrowing> borrowing = borrowings.stream().filter(item -> item.getBorrowable().equals(b)).findAny();
        return borrowing.isPresent() ? borrowing.get() : null;
    }

    public boolean isBorrowed(Borrowable item) {
        for (Borrowing b : borrowings) {
            if (((Equipment)b.getBorrowable()).equals(((Equipment)item))) {
                return true;
            }
        }
        return false;
    }

    public void addBorrowedItem(Borrowable item, Date borrowDate, String reason, Borrower borrower) {
        borrowings.add(new Borrowing(item, borrowDate, reason, borrower));
    }

    public void removeBorrowedItem(Borrowable item) throws NoSuchElementException {
        if (!isBorrowed(item)) throw new NoSuchElementException("The item is not borrowed.");

        borrowings.removeIf(i -> i.getBorrowable().equals(item));
    }

    public ArrayList<Borrowing> getBorrowings() {
        return borrowings;
    }

    public ArrayList<Borrowing> getBorrowings(String reason) {
        //TODO
        return null;
    }

    public ArrayList<Borrowing> getBorrowings(Borrower borrower) {
        ArrayList<Borrowing> borrowings = new ArrayList<>();
        for (Borrowing b : this.borrowings) {
            if (b.getBorrower().equals(borrower)) {
                borrowings.add(b);
            }
        }
        return borrowings;
    }

    public ArrayList<Borrowing> getLateBorrowings() {
        ArrayList<Borrowing> borrowings = new ArrayList<>();
        for (Borrowing b : this.borrowings) {
            if (b.isLate()) {
                borrowings.add(b);
            }
        }
        return borrowings;
    }
}
