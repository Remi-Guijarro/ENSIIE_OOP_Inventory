package inventory;

import inventory.stocks.Inventory;

import javax.swing.text.html.HTMLEditorKit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

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
        for (Borrowing b : borrowings) {
            if (b.getBorrowable().equals(item)) {
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
