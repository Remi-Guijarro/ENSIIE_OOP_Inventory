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

    /**
     * @return ArrayList<Borrowable> return the borrowed items
     */
    public ArrayList<Borrowable> getBorrowedItems() {
        ArrayList<Borrowable> borrowables = new ArrayList<>();
        for (Borrowing borrowing : borrowings) {
            borrowables.add(borrowing.getBorrowable());
        }
        return borrowables;
    }

    /**
     * @param b a Borrowable item
     * @return Borrowing
     *  return the borrowing from the borrowable or null if the given borrowable is not linked to Borrowing
     */
    public Borrowing getBorrowerFrom(Borrowable b){
        Optional<Borrowing> borrowing = borrowings.stream().filter(item -> item.getBorrowable().equals(b)).findAny();
        return borrowing.isPresent() ? borrowing.get() : null;
    }

    /**
     * @param {@link Borrowable} item
     * @return boolean
     * return true if the borrowable is borrowed, false otherwise
     */
    public boolean isBorrowed(Borrowable item) {
        for (Borrowing b : borrowings) {
            if (((Equipment)b.getBorrowable()).equals(((Equipment)item))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add new Borrowing concerning the given Borrowable, Borrower, Reason and BorrowDate
     * @param item
     * @param borrowDate
     * @param reason
     * @param borrower
     */
    public void addBorrowedItem(Borrowable item, Date borrowDate, String reason, Borrower borrower) {
        borrowings.add(new Borrowing(item, borrowDate, reason, borrower));
    }

    /**
     * @param item
     * @throws NoSuchElementException
     * Remove the Borrowing from given Borrowable
     */
    public void removeBorrowedItem(Borrowable item) throws NoSuchElementException {
        if (!isBorrowed(item)) throw new NoSuchElementException("The item is not borrowed.");

        borrowings.removeIf(i -> i.getBorrowable().equals(item));
    }

    /**
     * @return ArrayList<Borrowing> the list of Borrowings
     */
    public ArrayList<Borrowing> getBorrowings() {
        return borrowings;
    }

    public ArrayList<Borrowing> getBorrowings(String reason) {
        //TODO
        return null;
    }

    /**
     * @param borrower
     * @return ArrayList<Borrowing> the borrowing concerning the given borrower
     */
    public ArrayList<Borrowing> getBorrowings(Borrower borrower) {
        ArrayList<Borrowing> borrowings = new ArrayList<>();
        for (Borrowing b : this.borrowings) {
            if (b.getBorrower().equals(borrower)) {
                borrowings.add(b);
            }
        }
        return borrowings;
    }

    /**
     * @return ArrayList<Borrowing> the late borrowings
     */
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
