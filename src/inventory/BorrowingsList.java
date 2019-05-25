package inventory;

import java.util.ArrayList;
import java.util.List;

public class BorrowingsList {
    //private List<Borrowable> borrowedItems;
    private static BorrowingsList ourInstance = new BorrowingsList();

    public static BorrowingsList getInstance() {
        return ourInstance;
    }
    //////////////////////////////////////////////////////////////////

    /*private BorrowingsList() {
        this.borrowedItems = new ArrayList<Borrowable>();
    }*/

    //public List<Borrowable> getBorrowedItems(){return null;}

    //public boolean isBorrowed(Borrowable item){return false;}

    //public void addBorrowedItem(Borrowable item){}

    //public void removeBorrowedItel(Borrowable item){}

    public ArrayList<Borrowing> getBorrowings(){return null;}

    public List<Borrowing> getBorrowings(String reason){return null;}

    public List<Borrowing> getBorrowings(Borrower borrower){return null;}

    public List<Borrowing> getLateBorrowings(){return null;}
}
